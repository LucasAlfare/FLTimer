@file:OptIn(ExperimentalUnsignedTypes::class)

package com.lucasalfare.fltimer.core.data.persistence

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import com.lucasalfare.fltimer.core.*
import com.lucasalfare.fltimer.core.configuration.Config
import com.lucasalfare.fltimer.core.data.*
import com.lucasalfare.fltimer.core.data.session.DefaultSessionName
import com.lucasalfare.fltimer.core.scramble.getCategoryByCode
import java.io.File

class PersistenceManager : EventManageable() {

  private var configs: MutableMap<Config, Any> = mutableMapOf()
  private var sessions: MutableMap<String, Session> = mutableMapOf()

  private var currentActiveSessionName = DefaultSessionName

  private var writer = Writer()

  override fun init() {
    val f = File(APPLICATION_DATABASE_FILE_NAME)

    if (f.exists()) {
      val fileData = f.readBytes()
      // if file is not empty, reads its information
      if (fileData.isNotEmpty()) {
        // TODO: attempt to create better validation
        val reader = Reader(fileData.toUByteArray())
        val signature = reader.readString(FLTIMER_STRING_SIGNATURE.length)!!
        if (signature != FLTIMER_STRING_SIGNATURE)
          println("ARQUIVO INVALIDO!!!!!??????????????")

        configs[Config.UseInspection] = reader.readBoolean()
        configs[Config.ShowScramblesInDetailsUI] = reader.readBoolean()
        configs[Config.NetworkingModeOn] = reader.readBoolean()
        configs[Config.AskForTimerMode] = reader.readBoolean()

        val nSessions = reader.read2Bytes()

        val currentActiveSessionNameLength = reader.read1Byte()
        currentActiveSessionName = reader.readString(currentActiveSessionNameLength)!!

        repeat(nSessions) {
          val sessionNameLength = reader.read1Byte()
          val sessionName = reader.readString(sessionNameLength)!!
          val categoryCode = reader.read1Byte()

          val nextSession = Session(sessionName)
          val nSessionSolves = reader.read2Bytes()

          repeat(nSessionSolves) {
            val time = reader.read4Bytes()
            val scrambleLength = reader.read1Byte()
            val scramble = reader.readString(scrambleLength)!!
            val penaltyCode = reader.read1Byte()
            val commentLength = reader.read1Byte()
            val comment = reader.readString(commentLength)!!

            val nextSolve = Solve(
              time = time,
              scramble = scramble,
              penalty = getPenaltyByCode(penaltyCode),
              comment = comment
            )
            nextSession.solves += nextSolve
          }

          nextSession.category = getCategoryByCode(categoryCode)
          sessions[nextSession.name] = nextSession
        }
      }
    }

    while (!initiated) {
      if (configs.isNotEmpty() && sessions.isNotEmpty()) {
        updateBytes()
        notifyListeners(
          event = AppEvent.PersistenceUpdate,
          data = arrayOf(
            configs,
            arrayOf(
              // sending session name read, unfortunately doesn't work with SessionSwitch event.
              currentActiveSessionName,
              sessions
            )
          ),
          origin = this
        )

        initiated = true
        commitFile()
      }
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
        currentActiveSessionName = props[0] as String
        sessions = props[1] as MutableMap<String, Session>
        updateBytes()
      }

      AppEvent.SolvesUpdate -> {
        sessions[currentActiveSessionName]!!.solves = data as Solves
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
    writer.write1Byte(currentActiveSessionName.length)
    writer.writeString(currentActiveSessionName)
  }

  private fun updateSessionsBytes() {
    sessions.values.forEach { session ->
      writer.write1Byte(session.name.length)
      writer.writeString(session.name)
      writer.write1Byte(session.category.code)
      writer.write2Bytes(session.solves.size)

      session.solves.values.forEach { solve ->
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
