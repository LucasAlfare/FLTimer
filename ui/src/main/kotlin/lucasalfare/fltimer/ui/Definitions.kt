package lucasalfare.fltimer.ui

import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.EventListener
import lucasalfare.fltimer.core.Listenable

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