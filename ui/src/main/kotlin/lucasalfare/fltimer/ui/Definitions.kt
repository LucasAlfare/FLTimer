package lucasalfare.fltimer.ui

import androidx.compose.ui.unit.IntSize
import lucasalfare.fltimer.core.*
import lucasalfare.fltimer.core.data.Penalty
import lucasalfare.fltimer.core.data.Solves
import lucasalfare.fltimer.core.statistics.getStats
import java.text.SimpleDateFormat

const val GearCharacter = "\u2699"
const val WastebasketCharacter = "\uD83D\uDDD1"
const val ListCharacter = "\uD83D\uDCDD"
const val GraphicCharacter = "\uD83D\uDCC8"
const val WarningCharacter = "\u26A0\uFE0F"
const val ClipboardCharacter = "\uD83D\uDCCB"
const val HandWriteCharacter = "\u270d\ufe0f"
const val PreviousCharacter = "⏪"
const val NextCharacter = "⏩"

/**
 * Pre created instance of a [ComponentsManager] class to be used
 * over UI components.
 *
 * TODO: must this be placed in a Companion Object?
 */
val uiComponentsManager = ComponentsManager()

/**
 * Custom implementation of a class that can be listened and emit
 * events to others.
 *
 * The appropriated use of this class is to work together UI components
 * in order to make then emit application events and receive then as
 * well.
 */
class ComponentsManager : Listenable(), EventListener {

  private val callbacks = mutableListOf<(AppEvent, Any?) -> Unit>()

  override fun init() {

  }

  override fun onEvent(event: AppEvent, data: Any?) {
    callbacks.forEach { callback ->
      callback(event, data)
    }
  }

  fun addCallback(callback: (AppEvent, Any?) -> Unit): (AppEvent, Any?) -> Unit {
    if (!callbacks.contains(callback)) callbacks.add(callback)
    return callback
  }

  fun removeCallback(callback: (AppEvent, Any?) -> Unit) {
    callbacks.remove(callback)
  }
}

fun getSessionResume(solves: Solves, includeScrambles: Boolean = true): String {
  var clipboardContent = "Generated by the awesome FLTimer in ${
    SimpleDateFormat("dd/MM/yyyy, hh:mm:ss").format(
      getCurrentTime()
    )
  }\n\n"

  clipboardContent += "Statistics:\n"
  solves.getStats().forEach {
    clipboardContent += "${it.name}: ${it.result.toTimestamp()}\n"
  }

  clipboardContent += "\n"
  clipboardContent += "Solves (${solves.size}):\n"

  solves.values.forEachIndexed { index, solve ->
    val timeLabel = when (solve.penalty) {
      Penalty.PlusTwo -> "+${(solve.time + 2000).toTimestamp()}"
      Penalty.Dnf -> "DNF"
      else -> solve.time.toTimestamp()
    }

    clipboardContent += "${index + 1})  $timeLabel  "

    if (includeScrambles) {
      clipboardContent += "|  ${solve.scramble}"
    }

    clipboardContent += "\n"
  }

  return clipboardContent
}
