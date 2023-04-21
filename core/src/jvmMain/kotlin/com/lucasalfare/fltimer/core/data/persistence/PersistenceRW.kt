@file:OptIn(ExperimentalUnsignedTypes::class)

package com.lucasalfare.fltimer.core.data.persistence

import java.io.File

fun getStartupReader(): Reader {
  val f = File(APPLICATION_DATABASE_FILE_NAME)

  if (f.exists()) {
    val data = f.readBytes().toUByteArray()
    if (data.isNotEmpty()) {
      return Reader(data)
    }
  }

  val d = UByteArray(11)
  d[9] = 0u
  d[10] = 1u
  return Reader(d)
}
