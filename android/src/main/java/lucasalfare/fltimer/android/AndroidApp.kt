package lucasalfare.fltimer.android

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.ui.composables.Display
import lucasalfare.fltimer.ui.composables.Scramble
import lucasalfare.fltimer.ui.composables.StatisticDetails
import lucasalfare.fltimer.ui.uiManager

@Composable
fun AndroidApp() {
  var sized by remember { mutableStateOf(false) }
  var shouldHide by remember { mutableStateOf(false) }

  var currentPresentation by remember { mutableStateOf("app") }
  var currentStatisticResultText by remember { mutableStateOf("") }

  DisposableEffect(true) {
    val callback = uiManager.addCallback { appEvent, data ->
      when (appEvent) {
        AppEvent.TimerStarted -> {
          shouldHide = true
        }

        AppEvent.TimerFinished -> {
          shouldHide = false
        }

        AppEvent.StatisticsResponse -> {
          currentPresentation = "stats"
          currentStatisticResultText = data as String
        }

        else -> {}
      }
    }

    onDispose { uiManager.removeCallback(callback) }
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .onGloballyPositioned {
        sized = true
      }
  ) {
    if (sized) {
      if (currentPresentation == "app") {
        Box(
          modifier = Modifier
            .align(alignment = Alignment.TopCenter)
            .getMyNextModifier(shouldHide)
            .animateContentSize()
        ) {
          Scramble()
        }

        Box(modifier = Modifier.align(Alignment.Center)) {
          Display()
        }

        Box(
          modifier = Modifier
            .align(Alignment.BottomCenter)
            .getMyNextModifier(shouldHide)
        ) {
          AndroidDetails()
        }
      } else {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
        ) {
          StatisticDetails(textContent = currentStatisticResultText) {
            currentPresentation = "app"
          }
        }

//        Column(
//          modifier = Modifier
//            .fillMaxSize()
//            .padding(8.dp)
//        ) {
//          TextButton(
//            modifier = Modifier.padding(bottom = 8.dp),
//            onClick = {
//              currentPresentation = "app"
//            }
//          ) {
//            Text("<- Return")
//          }
//
//          TextField(
//            value = currentStatisticResultText,
//            modifier = Modifier.fillMaxSize(),
//            //readOnly = true,
//            onValueChange = {}
//          )
//        }
      }
    } else {
      Text(
        text = "FLTimer v1.0\nloading...",
        fontSize = 40.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier.align(Alignment.Center)
      )
    }
  }
}

private fun Modifier.getMyNextModifier(toggleableBool: Boolean) = this.then(
  if (toggleableBool)
    Modifier.size(0.dp)
  else
    Modifier.wrapContentSize()
)
