package lucasalfare.fltimer.ui.composables

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.toTimestamp
import lucasalfare.fltimer.ui.uiComponentsManager

@Composable
fun Display() {
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
    text = text,
    fontSize = 50.sp,
    fontFamily = FontFamily.Monospace,
    fontWeight = FontWeight.Bold
  )
}