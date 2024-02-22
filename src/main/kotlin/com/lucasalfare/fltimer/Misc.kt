package com.lucasalfare.fltimer

/**
 * Returns appropriated current time.
 */
fun getCurrentTime(useNanos: Boolean = false) =
  if (useNanos) System.nanoTime()
  else System.currentTimeMillis()

/**
 * Converts a number to timestamp string in the format following:
 *
 * `(mm:)ss.SSS`
 *
 * This is similar to the standard Java call:
 *
 *     System.out.println(new SimpleDateFormat("mm:ss.SSS").format(SOME_LONG_VALUE));
 *
 * @author Francisco Lucas
 */
fun Long.toTimestamp(): String {
  if (this == -1L) return "DNF"

  val seconds = "${(this / 1000) % 60}".padStart(2, '0')
  val milliseconds = "${this % 1000}".padStart(3, '0')

  if (this >= 60_000L) {
    val minutes = "${(this / 60_000L)}".padStart(2, '0')
    return "$minutes:$seconds.$milliseconds"
  }

  return "$seconds.$milliseconds"
}