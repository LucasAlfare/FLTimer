package lucasalfare.fltimer.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.toTimestamp

@Composable
fun Display(modifier: Modifier = Modifier) {
  var text by remember { mutableStateOf("ready") }

  DisposableEffect(true) {
    val callback = uiComponentsManager.addCallback { appEvent, data ->
      when (appEvent) {
        AppEvent.TimerUpdate -> {
          text = (data as Long).toTimestamp()
        }
      }
    }

    onDispose { uiComponentsManager.removeCallback(callback) }
  }

  Text(
    text = text
  )
}