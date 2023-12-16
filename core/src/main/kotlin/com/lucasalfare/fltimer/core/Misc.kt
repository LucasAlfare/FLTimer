package com.lucasalfare.fltimer.core

import java.text.SimpleDateFormat
import java.util.*

/**
 * Returns appropriated current time.
 */
fun getCurrentTime(useNanos: Boolean = false) =
  if (useNanos) System.nanoTime()
  else System.currentTimeMillis()