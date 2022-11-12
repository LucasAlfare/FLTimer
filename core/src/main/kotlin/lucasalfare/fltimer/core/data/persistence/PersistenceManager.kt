@file:OptIn(ExperimentalUnsignedTypes::class)

package lucasalfare.fltimer.core.data.persistence

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.EventManageable
import lucasalfare.fltimer.core.configuration.Config
import lucasalfare.fltimer.core.data.*
import lucasalfare.fltimer.core.toByteArray
import java.io.File

class PersistenceManager : EventManageable() {

  private var configs: MutableMap<Config, Any> = mutableMapOf()
  private var sessions: MutableMap<String, Session> = mutableMapOf()

  private var currentActiveSession = ""

  private var writer = Writer()
  private var initiated = false

  override fun init() {
    val f = File("fltimer_data.fltd")

    if (f.exists()) {
      val fileData = f.readBytes()
      // if file is not empty, reads its information
      if (fileData.isNotEmpty()) {
        val reader = Reader(fileData.toUByteArray())
        val signature = reader.readString(FLTIMER_STRING_SIGNATURE.length)!!
        if (signature != "fltimer") println("ARQUIVO INVALIDO")

        configs[Config.UseInspection] = reader.readBoolean()
        configs[Config.ShowScramblesInDetailsUI] = reader.readBoolean()
        configs[Config.NetworkingModeOn] = reader.readBoolean()
        configs[Config.AskForTimerMode] = reader.readBoolean()

        val nSessions = reader.read2Bytes()

        repeat(nSessions) {
          val sessionNameLength = reader.read1Byte()
          val sessionName = reader.readString(sessionNameLength)!!

          val currentSession = Session(sessionName)
          val nSessionSolves = reader.read2Bytes()

          repeat(nSessionSolves) {
            val time = reader.read4Bytes()
            val scrambleLength = reader.read1Byte()
            val scramble = reader.readString(scrambleLength)!!
            val penaltyCode = reader.read1Byte()
            val commentLength = reader.read1Byte()
            val comment = reader.readString(commentLength)!!

            currentSession.solves += Solve(
              time = time,
              scramble = scramble,
              penalty = getPenaltyByCode(penaltyCode),
              comment = comment
            )
          }

          sessions[currentSession.name] = currentSession
        }
      }
    }

    CoroutineScope(Job()).launch {
      while (true) {
        if (configs.isNotEmpty() && sessions.isNotEmpty()) {
          updateBytes()
          notifyListeners(
            event = AppEvent.PersistenceUpdate,
            data = arrayOf(configs, sessions),
            origin = this
          )
          initiated = true
          commitFile()
          break
        }
      }
      this.cancel()
    }
  }

  override fun onEvent(event: AppEvent, data: Any?, origin: Any?) {
    when (event) {
      AppEvent.ConfigsUpdate -> {
        configs = data as MutableMap<Config, Any>
        updateBytes()
      }

      AppEvent.SessionsUpdate -> {
        val props = data as Array<*>
        currentActiveSession = props[0] as String
        sessions = props[1] as MutableMap<String, Session>
        updateBytes()
      }

      AppEvent.SolvesUpdate -> {
        sessions[currentActiveSession]!!.solves = data as Solves
        updateBytes()
      }

      AppEvent.ApplicationFinish -> {
        updateBytes()
        commitFile()
      }

      else -> {}
    }
  }

  private fun updateBytes() {
    if (initiated) {
      writer.clearWritingData()
      updateHeaderBytes()
      updateSessionsBytes()
    }
  }

  private fun updateHeaderBytes() {
    writer.writeString(FLTIMER_STRING_SIGNATURE)
    writer.writeBoolean(configs[Config.UseInspection] as Boolean)
    writer.writeBoolean(configs[Config.ShowScramblesInDetailsUI] as Boolean)
    writer.writeBoolean(configs[Config.NetworkingModeOn] as Boolean)
    writer.writeBoolean(configs[Config.AskForTimerMode] as Boolean)
    writer.write2Bytes(sessions.size)
  }

  private fun updateSessionsBytes() {
    sessions.values.forEach {
      writer.write1Byte(it.name.length)
      writer.writeString(it.name)
      writer.write2Bytes(it.solves.size)

      it.solves.values.forEach { solve ->
        writer.write4Bytes(solve.time)
        writer.write1Byte(solve.scramble.length)
        writer.writeString(solve.scramble)
        writer.write1Byte(solve.penalty.toCode())
        writer.write1Byte(solve.comment.length)
        writer.writeString(solve.comment)
      }
    }
  }

  private fun commitFile() {
    File(APPLICATION_DATABASE_FILE_NAME)
      .writeBytes(
        writer.getData().toByteArray()
      )
  }
}
