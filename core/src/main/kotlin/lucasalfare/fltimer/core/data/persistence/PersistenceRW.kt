package lucasalfare.fltimer.core.data.persistence

import lucasalfare.fltimer.core.toIntArray
import java.io.File

private var reader: BytesReader? = null

fun setupFileBytesReader(): BytesReader? {
  if (reader == null) {
    val raw = File("fltimer_data.fltd")
    if (raw.exists()) {
      val rawBytes = raw.readBytes()
      reader = BytesReader(rawBytes.toIntArray())
    } else {
      val d = IntArray(11)
      d[9] = 0
      d[10] = 1
      reader = BytesReader(d)
    }
  }

  // TODO: validate bytes before return?
  return reader
}
