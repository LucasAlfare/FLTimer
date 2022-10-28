package lucasalfare.fltimer.ui

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.EventManageable

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
 * Pre created instance of a [UIManager] class to be used
 * over UI components.
 *
 * TODO: must this be placed in a Companion Object?
 */
val uiManager = UIManager()

fun Modifier.onFocusSelectAll(textFieldValueState: MutableState<TextFieldValue>): Modifier =
  composed(
    inspectorInfo = debugInspectorInfo {
      name = "textFieldValueState"
      properties["textFieldValueState"] = textFieldValueState
    }
  ) {
    var triggerEffect by remember {
      mutableStateOf<Boolean?>(null)
    }
    if (triggerEffect != null) {
      LaunchedEffect(triggerEffect) {
        val tfv = textFieldValueState.value
        textFieldValueState.value = tfv.copy(selection = TextRange(0, tfv.text.length))
      }
    }
    Modifier.onFocusChanged { focusState ->
      if (focusState.isFocused) {
        triggerEffect = triggerEffect?.let { bool ->
          !bool
        } ?: true
      }
    }
  }

/**
 * Custom implementation of a class that can be listened and emit
 * events to others.
 *
 * The appropriated use of this class is to work together UI components
 * in order to make then emit application events and receive then as
 * well.
 */
class UIManager : EventManageable() {

  private val callbacks = mutableListOf<(AppEvent, Any?) -> Unit>()

  override fun init() {}

  override fun onEvent(event: AppEvent, data: Any?, origin: Any?) {
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
