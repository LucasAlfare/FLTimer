package lucasalfare.fltimer.ui.composables

import androidx.compose.material.Text
import androidx.compose.runtime.*
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.ui.uiComponentsManager

@Composable
fun Scramble() {
  var text by remember { mutableStateOf("[not loaded yet]") }

  DisposableEffect(true) {
    val callback = uiComponentsManager.addCallback { appEvent, data ->
      when(appEvent) {
        AppEvent.ScrambleGenerated -> {
          val args = data as Array<*>
          text = args[1] as String
        }
      }
    }

    onDispose { uiComponentsManager.removeCallback(callback) }
  }

  Text(
    text = text
  )
}
