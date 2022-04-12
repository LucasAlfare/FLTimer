package lucasalfare.fltimer.ui.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.ui.uiComponentsManager

@Composable
fun Expandable(
  modifier: Modifier = Modifier
) {
  var parentHeight by remember { mutableStateOf(0) }
  var expandableHeight by remember { mutableStateOf(0) }
  val currentY = remember { Animatable(0f) }
  val targetY = remember { Animatable(0f) }
  var shouldExpand by remember { mutableStateOf(false) }


  DisposableEffect(true) {
    val callback = uiComponentsManager.addCallback { appEvent, _ ->
      when (appEvent) {
        AppEvent.EmptyEvent -> {
          shouldExpand = !shouldExpand
        }
      }
    }

    onDispose { uiComponentsManager.removeCallback(callback) }
  }

  // bounds
  Box(
    modifier = Modifier
      .onGloballyPositioned {

      }
      .fillMaxSize()
      .background(Color.Blue)
  ) {

    // expandable
    Box(
      modifier = Modifier
        .offset(x = 0.dp, y = currentY.value.dp)
        .onGloballyPositioned {
          expandableHeight = it.size.height
        }
        .fillMaxSize()
        .background(Color.Green)
    ) {

    }
  }
}
