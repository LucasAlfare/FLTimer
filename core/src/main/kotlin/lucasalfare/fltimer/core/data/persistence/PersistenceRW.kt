package lucasalfare.fltimer.core.data.persistence

import lucasalfare.fltimer.core.toIntArray
import java.io.File

private var reader: BytesReader? = null

fun setupFileBytesReader(): BytesReader? {
  if (reader == null) {
    val raw = File("fltimer_data.fltd")
    val rawBytes = raw.readBytes()
    reader = BytesReader(rawBytes.toIntArray())
  }

  // TODO: validate bytes before return
  return reader
}

