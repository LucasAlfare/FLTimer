package lucasalfare.fltimer.core


enum class AppMode { Default, Online, NotSet }

/**
 * Returns appropriated current time.
 */
fun getCurrentTime(useNanos: Boolean = false) =
  if (useNanos) System.nanoTime()
  else System.currentTimeMillis()