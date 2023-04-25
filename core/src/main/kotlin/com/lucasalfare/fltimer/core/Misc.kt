package com.lucasalfare.fltimer.core

import java.text.SimpleDateFormat
import java.util.*

/**
 * Returns appropriated current time.
 */
fun getCurrentTime(useNanos: Boolean = false) =
  if (useNanos) System.nanoTime()
  else System.currentTimeMillis()

fun Date.toString(
  format: String = "dd/mm/yyyy, HH:mm:ss",
  locale: Locale = Locale.getDefault()
): String {
  val formatter = SimpleDateFormat(format, locale)
  return formatter.format(this)
}

fun getCurrentDateTime(): Date {
  System.currentTimeMillis()
  return Calendar.getInstance().time
}