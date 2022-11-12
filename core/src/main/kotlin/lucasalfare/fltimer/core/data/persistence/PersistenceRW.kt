@file:OptIn(ExperimentalUnsignedTypes::class)

package lucasalfare.fltimer.core.data.persistence

import java.io.File

fun getStartupReader(): Reader {
  val f = File(APPLICATION_DATABASE_FILE_NAME)

  if (f.exists()) {
    val data = f.readBytes().toUByteArray()
    if (data.isNotEmpty()) {
      println("[RW] file data was loaded.")
      return Reader(data)
    }
  }

  val d = UByteArray(11)
  d[9] = 0u
  d[10] = 1u

  println("[RW] no file, loaded a tmp reader.")
  return Reader(d)
}
