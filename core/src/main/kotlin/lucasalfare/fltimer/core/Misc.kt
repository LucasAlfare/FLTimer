package lucasalfare.fltimer.core


class L {
  var logAllowed = true

  fun d(data: Any) {
    if (logAllowed) {
      print(data)
    }
  }
}

/**
 * Returns appropriated current time.
 */
fun getCurrentTime(nanos: Boolean = false) =
  if (nanos) System.nanoTime()
  else System.currentTimeMillis()