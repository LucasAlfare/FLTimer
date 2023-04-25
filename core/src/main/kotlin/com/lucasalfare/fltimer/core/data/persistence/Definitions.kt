package com.lucasalfare.fltimer.core.data.persistence

import com.lucasalfare.flbinary.Reader
import com.lucasalfare.flbinary.Writer
import com.lucasalfare.fltimer.core.configuration.Config
import com.lucasalfare.fltimer.core.data.*
import com.lucasalfare.fltimer.core.scramble.Category
import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path

const val FLTIMER_STRING_SIGNATURE = "fltimer"

/**
 * Default name of application database file.
 */
const val APPLICATION_DATABASE_FILE_NAME = "${FLTIMER_STRING_SIGNATURE}_data.fltd"

@OptIn(ExperimentalUnsignedTypes::class)
fun readFLTimerStateFromFile(): FLTimerState {
  val file = File(APPLICATION_DATABASE_FILE_NAME)
  val stateRef = FLTimerState.getFLTimerState()

  if (file.exists()) {
    val reader = Reader(file.readBytes().toUByteArray())
    val fltimerSignature = reader.readString(length = 7)!!

    if (fltimerSignature != FLTIMER_STRING_SIGNATURE) {
      return stateRef
    }

    stateRef.configurations[Config.UseInspection] = reader.readBoolean()
    stateRef.configurations[Config.ShowScramblesInDetailsUI] = reader.readBoolean()
    stateRef.configurations[Config.NetworkingModeOn] = reader.readBoolean()
    stateRef.configurations[Config.AskForTimerMode] = reader.readBoolean()

    val nSessions = reader.read2Bytes()

    val currentActiveSessionNameLength = reader.read1Byte()
    stateRef.currentActiveSessionName = reader.readString(currentActiveSessionNameLength)!!

    repeat(nSessions) {
      val nextSession = Session(
        name = reader.readString(reader.read1Byte())!!,
        category = Category.getCategoryByCode(reader.read1Byte())
      )

      val nSessionSolves = reader.read2Bytes()

      repeat(nSessionSolves) {
        val nextSolve = Solve(
          time = reader.read4Bytes(),
          scramble = reader.readString(reader.read1Byte())!!,
          penalty = Penalty.getPenaltyByCode(reader.read1Byte()),
          comment = reader.readString(reader.read1Byte())!!
        )

        nextSession.solves += nextSolve
      }

      stateRef.sessions += nextSession
    }
  }

  return stateRef
}

@OptIn(ExperimentalUnsignedTypes::class)
fun writeFLTimerStateToFile() {
  val stateRef = FLTimerState.getFLTimerState()
  val writer = Writer()

  writer.writeString(FLTIMER_STRING_SIGNATURE)
  writer.writeBoolean(stateRef.configurations[Config.UseInspection] as Boolean)
  writer.writeBoolean(stateRef.configurations[Config.ShowScramblesInDetailsUI] as Boolean)
  writer.writeBoolean(stateRef.configurations[Config.NetworkingModeOn] as Boolean)
  writer.writeBoolean(stateRef.configurations[Config.AskForTimerMode] as Boolean)

  writer.write2Bytes(stateRef.sessions.size)

  writer.write1Byte(stateRef.currentActiveSessionName.length)
  writer.writeString(stateRef.currentActiveSessionName)

  stateRef.sessions.forEach { session ->
    writer.write1Byte(session.name.length)
    writer.writeString(session.name)
    writer.write1Byte(session.category.code)
    writer.write2Bytes(session.solves.size)

    session.solves.values.forEach { solve ->
      writer.write4Bytes(solve.time)
      writer.write1Byte(solve.scramble.length)
      writer.writeString(solve.scramble)
      writer.write1Byte(solve.penalty.code)
      writer.write1Byte(solve.comment.length)
      writer.writeString(solve.comment)
    }
  }

  Files.deleteIfExists(Path(APPLICATION_DATABASE_FILE_NAME))

  val targetFile = File(APPLICATION_DATABASE_FILE_NAME)
  targetFile.writeBytes(writer.getData().toByteArray())
}

fun main() {
  println("Hewhehehaskdjhf")
}