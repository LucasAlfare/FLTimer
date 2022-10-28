package lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.ui.uiManager

@Composable
fun Scramble() {
  var text by remember { mutableStateOf("loading....") }

  LaunchedEffect(true) {
    uiManager.notifyListeners(AppEvent.RequestScrambleGenerated)
  }

  DisposableEffect(true) {
    val callback = uiManager.addCallback { appEvent, data ->
      when (appEvent) {
        AppEvent.ScrambleGenerated -> {
          val args = data as Array<*>
          text = args[1] as String
        }
        else -> {}
      }
    }

    onDispose { uiManager.removeCallback(callback) }
  }

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(80.dp)
      //.defaultMinSize(minWidth = 200.dp, minHeight = 70.dp)
      .padding(12.dp)
  ) {
    Text(
      modifier = Modifier.align(Alignment.Center),
      text = text,
      fontSize = 20.sp,
      fontWeight = FontWeight.Bold,
      textAlign = TextAlign.Center,
      fontFamily = FontFamily.Monospace
    )
  }
}
