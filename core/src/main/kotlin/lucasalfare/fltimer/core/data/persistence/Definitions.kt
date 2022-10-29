@Suppress("MemberVisibilityCanBePrivate")
class BytesReader(private val data: IntArray) {

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

  fun seek(nextPos: Int): Int {
    if (nextPos < data.size) {
      return -1
    }

    position = nextPos
    return position
  }

  private fun updateTmpBuffer(nBytes: Int): Boolean {
    if (position + nBytes >= data.size) return false
    repeat(nBytes) { tmpBuffer[it] = data[position + it] }
    position += nBytes
    return true
  }
}

class BytesWriter(size: Int) {

  val data = Array(size) { 0 }
  var offset = 0

  fun writeByte(byte: Int) {
    data[offset++] = byte
  }

  fun writeShort(short: Int) {

  }

  fun writeInt(int: Int) {

  }

  fun writeString(string: String) {

  }
}

fun main() {
  //debugging
}