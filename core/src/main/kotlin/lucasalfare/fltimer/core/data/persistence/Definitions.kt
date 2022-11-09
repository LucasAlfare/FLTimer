@file:Suppress("MemberVisibilityCanBePrivate")

package lucasalfare.fltimer.core.data.persistence

import lucasalfare.fltimer.core.data.Penalty
import lucasalfare.fltimer.core.data.Session
import lucasalfare.fltimer.core.data.Solve
import lucasalfare.fltimer.core.data.Solves
import lucasalfare.fltimer.core.toByteArray
import java.io.File

/**
 * This class encapsulates the task of reading bytes from an single Array.
 *
 * Normally the [data] field represents bytes that comes from some file that should be
 * read.
 */
class BytesReader(private val data: IntArray) {

  /**
   * This field indicates the current offset that is being read.
   * It is constantly updated as something is read, being a dynamic mark.
   */
  var position = 0

  private val tmpBuffer = IntArray(4) { 0 }

  fun read1Byte(customPosition: Int = position): Int {
    seek(customPosition)
    if (updateTmpBuffer(1)) return (tmpBuffer[0])
    return -1
  }

  fun readBoolean(customPosition: Int = position) = read1Byte(customPosition) == 1

  fun read2Bytes(customPosition: Int = position): Int {
    seek(customPosition)
    if (updateTmpBuffer(2)) {
      return tmpBuffer[0] shl 8 or
              tmpBuffer[1]
    }
    return -1
  }

  fun read3Bytes(customPosition: Int = position): Int {
    seek(customPosition)
    if (updateTmpBuffer(3)) {
      return tmpBuffer[0] shl 16 or
              (tmpBuffer[1] shl 8 or
                      tmpBuffer[2])
    }
    return -1
  }

  fun read4Bytes(customPosition: Int = position): Int {
    seek(customPosition)
    if (updateTmpBuffer(4)) {
      return tmpBuffer[0] shl 24 or
              (tmpBuffer[1] shl 16 or
                      (tmpBuffer[2] shl 8 or
                              tmpBuffer[3]))
    }
    return -1
  }

  fun readString(stringLength: Int): String? {
    if (position + stringLength > data.size) return null

    var result = ""
    data.slice(position..(position - 1 + stringLength)).forEach { result += Char(it) }
    return result
  }

  fun seek(nextPos: Int): Int {
    if (nextPos >= data.size) {
      return -1
    }

    position = nextPos
    return position
  }

  private fun updateTmpBuffer(nBytes: Int): Boolean {
    if (position + nBytes > data.size) return false
    repeat(nBytes) { tmpBuffer[it] = data[position + it] }
    position += nBytes
    return true
  }
}

/**
 * This class encapsulates the task of writing bytes to an single Array.
 *
 * Normally the [data] field represents bytes that should be recorded to an file.
 */
class BytesWriter {

  private val data = mutableListOf<Int>()

  fun write1Byte(value: Int) {
    data += value
  }

  fun writeBoolean(value: Boolean) {
    this.write1Byte(if (value) 1 else 0)
  }

  fun write2Bytes(value: Int) {
    data += value shr 8
    data += value and 0xff
  }

  fun write3Bytes(value: Int) {
    data += ((value shr 16) and 0xff)
    data += ((value shr 8) and 0xff)
    data += ((value shr 0) and 0xff)
  }

  fun write4Bytes(value: Long) {
    data += ((value shr 24) and 0xff).toInt()
    data += ((value shr 16) and 0xff).toInt()
    data += ((value shr 8) and 0xff).toInt()
    data += ((value shr 0) and 0xff).toInt()
  }

  fun writeString(value: String) {
    value.toCharArray().forEach {
      write1Byte(it.code)
    }
  }

  fun getData() = data.toIntArray()

  override fun toString(): String {
    var res = ""
    data.forEachIndexed { index, i ->
      res += "0x${Integer.toHexString(i).padStart(2, '0')} "
      if ((index + 1) % 10 == 0) {
        res += "\n"
      }
    }
    return res
  }
}

/**
 * EXAMPLE usage of the bytes reader/writer
 */
fun main() {
  val writer = BytesWriter()

  val solves1 = Solves(
    Solve(time = 10000L, scramble = "R U F"),
    Solve(time = 10400L, scramble = "X Y Z L U F")
  )
  val session1 = Session("sessao teste 1", solves1)

  val solves2 = Solves(
    Solve(time = 9999L, scramble = "R U F"),
    Solve(time = 1111L, scramble = "R U F"),
    Solve(time = 2222L, scramble = "R U F"),
    Solve(time = 3333L, scramble = "R U F")
  )
  val session2 = Session("sessao teste 2", solves2)

  val sessions = arrayOf(
    session1, session2
  )

  val fltimerSignature = "fltimer"
  val useInspection = false
  val showScramblesInDetailsUi = false
  val networkingModeOn = false
  val askForTimerMode = false
  val nSessions = sessions.size

  writer.writeString(fltimerSignature)
  writer.writeBoolean(useInspection)
  writer.writeBoolean(showScramblesInDetailsUi)
  writer.writeBoolean(networkingModeOn)
  writer.writeBoolean(askForTimerMode)
  writer.write2Bytes(nSessions)

  sessions.forEach {
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
          Penalty.PlusTwo -> 2
          else -> 3
        }
      )
      writer.write1Byte(s.comment.length)
      writer.writeString(s.comment)
      writer.write1Byte(s.id.toString().length)
      writer.writeString(s.id.toString())
    }

    writer.write1Byte(0xff)
  }

  println(writer)

  File("fltimer_data.fltd").writeBytes(writer.getData().toByteArray())
}
