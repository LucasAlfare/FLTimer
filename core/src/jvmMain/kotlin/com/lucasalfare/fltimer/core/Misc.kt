package com.lucasalfare.fltimer.core

import com.lucasalfare.fltimer.core.configuration.Config
import com.lucasalfare.fltimer.core.data.Solves
import java.text.SimpleDateFormat
import java.util.*

/**
 * Returns appropriated current time.
 */
fun getCurrentTime(useNanos: Boolean = false) =
  if (useNanos) System.nanoTime()
  else System.currentTimeMillis()

fun Date.toString(format: String = "dd/mm/yyyy, HH:mm:ss", locale: Locale = Locale.getDefault()): String {
  val formatter = SimpleDateFormat(format, locale)
  return formatter.format(this)
}

fun getCurrentDateTime(): Date {
  return Calendar.getInstance().time
}

data class FLTimerStateModel(
  val data: Solves,
  val configurations: MutableMap<Config, Any>
) {
  companion object {
    fun getFLTimerStateModel(): FLTimerStateModel {
      return FLTimerStateModel(
        data = Solves(),
        configurations = mutableMapOf(
          Pair(Config.UseInspection, false),
          Pair(Config.ShowScramblesInDetailsUI, false),
          Pair(Config.NetworkingModeOn, false),
          Pair(Config.AskForTimerMode, true)
        )
      )
    }
  }
}