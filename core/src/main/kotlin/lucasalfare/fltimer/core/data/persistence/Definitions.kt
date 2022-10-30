@file:Suppress("MemberVisibilityCanBePrivate")
package lucasalfare.fltimer.core.data.persistence

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

  override fun toString() =
    data.map {
      "0x${Integer.toHexString(it)}"
    }.toString()
}
