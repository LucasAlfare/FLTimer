package lucasalfare.fltimer.desktop

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.ui.composables.*
import lucasalfare.fltimer.ui.uiManager


@Composable
fun DefaultDesktopApp() {
  var currentPresentation by remember { mutableStateOf("app") }
  var currentStatisticResultText by remember { mutableStateOf("") }

  DisposableEffect(true) {
    val callback = uiManager.addCallback { appEvent, data ->
      when (appEvent) {
        AppEvent.StatisticsResponse -> {
          currentPresentation = "stats"
          currentStatisticResultText = data as String
        }
        else -> {}
      }
    }

    onDispose { uiManager.removeCallback(callback) }
  }

  if (currentPresentation == "app") {
    Row {
      Box(
        modifier = Modifier
          //.width(120.dp)
          .weight(0.75f)
          .padding(bottom = 8.dp)
      ) {
        Column {
          SessionController()
          TimesList()
        }
      }

      Box(modifier = Modifier.weight(2f)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Box(modifier = Modifier.weight(1f)) {
            Scramble()
          }

          Box(
            modifier = Modifier.weight(2f),
            contentAlignment = Alignment.TopCenter
          ) {
            Display()
          }
        }
      }

      Box(
        modifier = Modifier
          .fillMaxHeight()
          //.width(120.dp)
          .weight(0.75f)
          .padding(bottom = 8.dp)
      ) {
        StatisticsList()
      }
    }
  } else {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
    ) {
      StatisticDetails(currentStatisticResultText) { currentPresentation = "app" }
    }
  }
}
