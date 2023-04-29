package com.lucasalfare.fltimer.core.model.data.persistence

import com.lucasalfare.flbinary.Reader
import com.lucasalfare.flbinary.Writer
import com.lucasalfare.fltimer.core.configuration.Config
import com.lucasalfare.fltimer.core.model.FLTimerModel
import com.lucasalfare.fltimer.core.model.Penalty
import com.lucasalfare.fltimer.core.model.Solve
import com.lucasalfare.fltimer.core.model.session.Session
import com.lucasalfare.fltimer.core.scramble.Category
import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path

const val FLTIMER_STRING_SIGNATURE = "fltimer"

/**
 * Default name of application database file.
 */
const val APPLICATION_DATABASE_FILE_NAME = "${FLTIMER_STRING_SIGNATURE}_data.fltd"

/**
 * This method reads the binary data from the application data file
 * if it exists. Either if the data file exists or not, this method
 * defines an initial application state, based on defaul values for
 * all needed fiels.
 */
@OptIn(ExperimentalUnsignedTypes::class)
fun readAndDefineFLTimerStateFromFile(onLoadDatabaseFile: () -> File) {
  val file = onLoadDatabaseFile()

  if (file.exists()) {
    val fileBytes = file.readBytes()
    val reader = Reader(fileBytes.toUByteArray())
    val fltimerSignature = reader.readString(length = 7)!!

    if (fltimerSignature != FLTIMER_STRING_SIGNATURE) {
      println("file signature doesnt' match!")
      return
    }

    FLTimerModel.configurations[Config.UseInspection] = reader.readBoolean()
    FLTimerModel.configurations[Config.ShowScramblesInDetailsUI] = reader.readBoolean()
    FLTimerModel.configurations[Config.NetworkingModeOn] = reader.readBoolean()
    FLTimerModel.configurations[Config.AskForTimerMode] = reader.readBoolean()

    val nSessions = reader.read2Bytes()

    val currentActiveSessionNameLength = reader.read1Byte()
    FLTimerModel.currentActiveSessionName.value = reader.readString(currentActiveSessionNameLength)!!

    repeat(nSessions) {
      val readSession = Session(
        name = reader.readString(reader.read1Byte())!!,
        category = Category.getCategoryByCode(reader.read1Byte())
      )

      val sessionSearch = FLTimerModel.sessions.firstOrNull {
        it.name == readSession.name
      }

      val nextSession = sessionSearch ?: readSession

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

      if (sessionSearch == null) {
        FLTimerModel.sessions += nextSession
      }
    }
  }

//  val file = File(APPLICATION_DATABASE_FILE_NAME)
//
//  if (file.exists()) {
//    val fileBytes = file.readBytes()
//    val reader = Reader(fileBytes.toUByteArray())
//    val fltimerSignature = reader.readString(length = 7)!!
//
//    if (fltimerSignature != FLTIMER_STRING_SIGNATURE) {
//      println("file signature doesnt' match!")
//      return
//    }
//
//    FLTimerModel.configurations[Config.UseInspection] = reader.readBoolean()
//    FLTimerModel.configurations[Config.ShowScramblesInDetailsUI] = reader.readBoolean()
//    FLTimerModel.configurations[Config.NetworkingModeOn] = reader.readBoolean()
//    FLTimerModel.configurations[Config.AskForTimerMode] = reader.readBoolean()
//
//    val nSessions = reader.read2Bytes()
//
//    val currentActiveSessionNameLength = reader.read1Byte()
//    FLTimerModel.currentActiveSessionName.value = reader.readString(currentActiveSessionNameLength)!!
//
//    repeat(nSessions) {
//      val readSession = Session(
//        name = reader.readString(reader.read1Byte())!!,
//        category = Category.getCategoryByCode(reader.read1Byte())
//      )
//
//      val sessionSearch = FLTimerModel.sessions.firstOrNull {
//        it.name == readSession.name
//      }
//
//      val nextSession = sessionSearch ?: readSession
//
//      val nSessionSolves = reader.read2Bytes()
//
//      repeat(nSessionSolves) {
//        val nextSolve = Solve(
//          time = reader.read4Bytes(),
//          scramble = reader.readString(reader.read1Byte())!!,
//          penalty = Penalty.getPenaltyByCode(reader.read1Byte()),
//          comment = reader.readString(reader.read1Byte())!!
//        )
//
//        nextSession.solves += nextSolve
//      }
//
//      if (sessionSearch == null) {
//        FLTimerModel.sessions += nextSession
//      }
//    }
//  }
}

@OptIn(ExperimentalUnsignedTypes::class)
fun writeFLTimerStateToFile(onWriteDatabaseFile: (Writer) -> Unit) {
  val writer = Writer()

  writer.writeString(FLTIMER_STRING_SIGNATURE)
  writer.writeBoolean(FLTimerModel.configurations[Config.UseInspection] as Boolean)
  writer.writeBoolean(FLTimerModel.configurations[Config.ShowScramblesInDetailsUI] as Boolean)
  writer.writeBoolean(FLTimerModel.configurations[Config.NetworkingModeOn] as Boolean)
  writer.writeBoolean(FLTimerModel.configurations[Config.AskForTimerMode] as Boolean)

  writer.write2Bytes(FLTimerModel.sessions.size)

  writer.write1Byte(FLTimerModel.currentActiveSessionName.value.length)
  writer.writeString(FLTimerModel.currentActiveSessionName.value)

  FLTimerModel.sessions.forEach { session ->
    writer.write1Byte(session.name.length)
    writer.writeString(session.name)
    writer.write1Byte(session.category.code)
    writer.write2Bytes(session.solves.size)

    session.solves.forEach { solve ->
      writer.write4Bytes(solve.time)
      writer.write1Byte(solve.scramble.length)
      writer.writeString(solve.scramble)
      writer.write1Byte(solve.penalty.code)
      writer.write1Byte(solve.comment.length)
      writer.writeString(solve.comment)
    }
  }

//  Files.deleteIfExists(Path(APPLICATION_DATABASE_FILE_NAME))
//  val targetFile = File(APPLICATION_DATABASE_FILE_NAME)
//  targetFile.writeBytes(writer.getData().toByteArray())

  onWriteDatabaseFile(writer)
}
