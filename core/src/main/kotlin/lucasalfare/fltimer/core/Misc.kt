package lucasalfare.fltimer.core

/**
 * Returns appropriated current time.
 */
fun getCurrentTime(nanos: Boolean = false) =
  if (nanos) System.nanoTime()
  else System.currentTimeMillis()