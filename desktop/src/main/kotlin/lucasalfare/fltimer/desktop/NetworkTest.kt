@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package lucasalfare.fltimer.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.getCurrentTime
import lucasalfare.fltimer.core.networking.NetworkManager
import lucasalfare.fltimer.core.setupManagers
import lucasalfare.fltimer.core.timer.TimerManager
import lucasalfare.fltimer.core.toTimestamp
import lucasalfare.fltimer.ui.composables.Display
import lucasalfare.fltimer.ui.uiComponentsManager

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
  Window(
    state = WindowState(
      position = WindowPosition(Alignment.Center),
      //placement = WindowPlacement.Maximized
    ),
    onKeyEvent = {
      if (it.key == Key.Spacebar) {
        when (it.type) {
          KeyEventType.KeyDown -> {
            uiComponentsManager.notifyListeners(
              event = AppEvent.TimerToggleDown,
              data = getCurrentTime(),
              origin = this // this pointing to curr context :(
            )
          }

          KeyEventType.KeyUp -> {
            uiComponentsManager.notifyListeners(
              event = AppEvent.TimerToggleUp,
              data = getCurrentTime(),
              origin = this // this pointing to curr context :(
            )
          }
        }
      } else if (it.key == Key.Escape) {
        uiComponentsManager.notifyListeners(AppEvent.TimerCancel)
      }
      false
    },
    onCloseRequest = ::exitApplication
  ) {
    // before show UI, sets managers up
    LaunchedEffect(true) {
      setupManagers(
        uiComponentsManager,
        TimerManager(),
        NetworkManager()
      )
    }

    // ...then show UI
    TestApp()
  }
}

@Composable
private fun TestApp() {
  Column(modifier = Modifier.fillMaxSize()) {
    Box(modifier = Modifier.padding(24.dp)) {
      Display()
    }

//    Box(modifier = Modifier.fillMaxSize()) {
//      Row {
//        UserTimes("User 1", longArrayOf(1000, 2000, 3000, 4000))
//        UserTimes("User 2", longArrayOf(10000, 20000, 30000, 40000))
//        UserTimes("User 3", longArrayOf(1000, 2000, 3000, 4000))
//        UserTimes("User 4", longArrayOf(1000, 2000, 3000, 4000))
//      }
//    }
  }
}

@Composable
fun UserTimes(userId: String, times: LongArray) {
  Box(modifier = Modifier.fillMaxHeight().width(100.dp).border(5.dp, Color.Gray).padding(12.dp)) {
    Column {
      Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
        Text(userId)
      }

      LazyColumn {
        times.forEachIndexed { index, l ->
          item {
            Text("${index + 1}. ${l.toTimestamp()}")
            Divider()
          }
        }
      }
    }
  }
}