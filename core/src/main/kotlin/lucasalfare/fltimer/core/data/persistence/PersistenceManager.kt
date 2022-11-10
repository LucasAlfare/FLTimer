package lucasalfare.fltimer.core.data.persistence

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.EventManageable
import lucasalfare.fltimer.core.configuration.Config
import lucasalfare.fltimer.core.data.Penalty
import lucasalfare.fltimer.core.data.Session
import lucasalfare.fltimer.core.data.Solve
import lucasalfare.fltimer.core.data.getPenaltyByCode
import lucasalfare.fltimer.core.toByteArray
import lucasalfare.fltimer.core.toIntArray
import java.io.File

class PersistenceManager : EventManageable() {

  private var configs: MutableMap<Config, Any> = mutableMapOf()
  private var sessions: MutableMap<String, Session> = mutableMapOf()
  private var writer = BytesWriter()
  private var initiated = false

  override fun init() {
    val f = File("fltimer_data.fltd")
    if (f.exists()) {
      val reader = BytesReader(f.readBytes().toIntArray())

      var signature = ""
      repeat("fltimer".length) { signature += Char(reader.read1Byte()) }

      if (signature != "fltimer") println("ARQUIVO INVALIDO")

      configs[Config.UseInspection] = reader.readBoolean()
      configs[Config.ShowScramblesInDetailsUI] = reader.readBoolean()
      configs[Config.NetworkingModeOn] = reader.readBoolean()
      configs[Config.AskForTimerMode] = reader.readBoolean()

      val nSessions = reader.read2Bytes()

      repeat(nSessions) {
        val sessionNameLength = reader.read1Byte()
        var sessionName = ""
        repeat(sessionNameLength) { sessionName += Char(reader.read1Byte()) }

        val currentSession = Session(sessionName)
        val nSessionSolves = reader.read2Bytes()

        repeat(nSessionSolves) {
          val time = reader.read3Bytes()
          val scrambleLength = reader.read1Byte()
          var scramble = ""
          repeat(scrambleLength) { scramble += Char(reader.read1Byte()) }
          val penaltyCode = reader.read1Byte()
          val commentLength = reader.read1Byte()
          var comment = ""
          repeat(commentLength) { comment += Char(reader.read1Byte()) }

          currentSession.solves += Solve(
            time = time.toLong(),
            scramble = scramble,
            penalty = getPenaltyByCode(penaltyCode),
            comment = comment
          )
        }

        sessions[currentSession.name] = currentSession
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
        sessions = props[1] as MutableMap<String, Session>
        updateBytes()
      }

      AppEvent.ApplicationFinish -> {
        commitFile()
      }

      else -> {}
    }
  }

  private fun updateBytes() {
    if (initiated) {
      writer.clearWritingData()
      updateConfigsBytes()
      updateSessionsBytes()
    }
  }

  private fun updateConfigsBytes() {
    writer.writeString("fltimer")
    writer.writeBoolean(configs[Config.UseInspection] as Boolean)
    writer.writeBoolean(configs[Config.ShowScramblesInDetailsUI] as Boolean)
    writer.writeBoolean(configs[Config.NetworkingModeOn] as Boolean)
    writer.writeBoolean(configs[Config.AskForTimerMode] as Boolean)
  }

  private fun updateSessionsBytes() {
    writer.write2Bytes(sessions.size)

    sessions.values.forEach {
      writer.write1Byte(it.name.length)
      writer.writeString(it.name)
      writer.write2Bytes(it.solves.size)

      it.solves.values.forEach { s ->
        writer.write3Bytes(s.time.toInt())
        writer.write1Byte(s.scramble.length)
        writer.writeString(s.scramble)
        writer.write1Byte(
          when (s.penalty) {
            Penalty.Ok -> 0
            Penalty.PlusTwo -> 1
            else -> 2
          }
        )
        writer.write1Byte(s.comment.length)
        writer.writeString(s.comment)
      }
    }
  }

  private fun commitFile() {
    File("fltimer_data.fltd").writeBytes(writer.getData().toByteArray())
  }
}
