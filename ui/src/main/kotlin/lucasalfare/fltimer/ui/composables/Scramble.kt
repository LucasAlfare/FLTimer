package lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.ui.uiComponentsManager

@Composable
fun Scramble() {
  var text by remember { mutableStateOf("loading....") }

  LaunchedEffect(true) {
    uiComponentsManager.notifyListeners(AppEvent.RequestScrambleGenerated)
  }

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

  Box(
    modifier = Modifier
      .padding(12.dp)
  ) {
    Text(
      text = text,
      textAlign = TextAlign.Center,
      fontFamily = FontFamily.Monospace
    )
  }
}
