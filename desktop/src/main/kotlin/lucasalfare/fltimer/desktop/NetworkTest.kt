@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package lucasalfare.fltimer.desktop

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.configuration.Config
import lucasalfare.fltimer.core.configuration.ConfigurationManager
import lucasalfare.fltimer.core.data.SolvesManager
import lucasalfare.fltimer.core.data.session.SessionManager
import lucasalfare.fltimer.core.getCurrentTime
import lucasalfare.fltimer.core.networking.NetworkManager
import lucasalfare.fltimer.core.scramble.ScrambleManager
import lucasalfare.fltimer.core.setupManagers
import lucasalfare.fltimer.core.timer.TimerManager
import lucasalfare.fltimer.core.toTimestamp
import lucasalfare.fltimer.ui.composables.Display
import lucasalfare.fltimer.ui.uiManager

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
  var currentWindowState by remember {
    mutableStateOf(
      WindowState(
        position = WindowPosition(Center),
        size = DpSize(400.dp, 200.dp)
      )
    )
  }

  var targetOnKeyEventCallback by remember {
    mutableStateOf(
      fun(_: KeyEvent): Boolean { return false }
    )
  }

  Window(
    state = currentWindowState,
    onCloseRequest = this::exitApplication,
    onKeyEvent = {
      targetOnKeyEventCallback(it)
    }
  ) {
    var appMode by remember { mutableStateOf("default") }
    var modeWasSelected by remember { mutableStateOf(false) }

    if (!modeWasSelected) {
      Column {
        Text("Choose FLTimer mode:")
        Row {
          TextButton(onClick = { appMode = "default"; modeWasSelected = true }) {
            Text("Default")
          }

          TextButton(onClick = { appMode = "online"; modeWasSelected = true }) {
            Text("Online")
          }
        }
      }
    } else {
      currentWindowState = WindowState(
        position = WindowPosition(Center),
        size = DpSize(700.dp, 400.dp)
      )

      targetOnKeyEventCallback = fun(keyEvent: KeyEvent): Boolean {

        if (keyEvent.key == Key.Spacebar) {
          when (keyEvent.type) {
            KeyEventType.KeyDown -> {
              uiManager.notifyListeners(
                event = AppEvent.TimerToggleDown,
                data = getCurrentTime(),
                origin = this // this pointing to curr context :(
              )
            }

            KeyEventType.KeyUp -> {
              uiManager.notifyListeners(
                event = AppEvent.TimerToggleUp,
                data = getCurrentTime(),
                origin = this // this is pointing to curr context :(
              )
            }
          }
        } else if (keyEvent.key == Key.Escape) {
          uiManager.notifyListeners(
            event = AppEvent.TimerCancel,
            origin = this
          )
        }
        return false
      }

      if (appMode == "default") {
        LaunchedEffect(true) {
          setupManagers(
            uiManager,
            ScrambleManager(),
            SolvesManager(),
            SessionManager(),
            TimerManager(),
            ConfigurationManager()
          )

          uiManager.notifyListeners(
            event = AppEvent.ConfigSet,
            data = arrayOf(Config.NetworkingModeOn, false),
            origin = this
          )
        }

        // show UI
        DefaultDesktopApp()
      } else {
        LaunchedEffect(true) {
          setupManagers(
            uiManager,
            ConfigurationManager(),
            TimerManager(),
            NetworkManager()
          )

          uiManager.notifyListeners(
            event = AppEvent.ConfigSet,
            data = arrayOf(Config.NetworkingModeOn, true),
            origin = this
          )
        }

        // show UI
        TestApp()
      }
    }
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