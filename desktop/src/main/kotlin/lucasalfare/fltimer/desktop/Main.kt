@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package lucasalfare.fltimer.desktop

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.AppMode
import lucasalfare.fltimer.core.configuration.Config
import lucasalfare.fltimer.core.configuration.ConfigurationManager
import lucasalfare.fltimer.core.data.SolvesManager
import lucasalfare.fltimer.core.data.persistence.PersistenceManager
import lucasalfare.fltimer.core.data.session.SessionManager
import lucasalfare.fltimer.core.getCurrentTime
import lucasalfare.fltimer.core.networking.NetworkManager
import lucasalfare.fltimer.core.scramble.ScrambleManager
import lucasalfare.fltimer.core.setupManagers
import lucasalfare.fltimer.core.timer.TimerManager
import lucasalfare.fltimer.ui.composables.Display
import lucasalfare.fltimer.ui.uiManager

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
  // TODO initial database load here
  val loadedCurrentMode = AppMode.Default // the value should comes from database file
  var currentWindowSize by remember { mutableStateOf(DpSize(400.dp, 200.dp)) }

  Window(
    state = WindowState(
      position = WindowPosition(Alignment.Center),
      size = currentWindowSize
    ),
    onCloseRequest = this::exitApplication,
    onKeyEvent = {
      if (it.key == Key.Spacebar) {
        when (it.type) {
          KeyEventType.KeyDown -> {
            uiManager.notifyListeners(
              event = AppEvent.TimerToggleDown,
              data = getCurrentTime(),
              origin = "[MainClass]"
            )
          }

          KeyEventType.KeyUp -> {
            uiManager.notifyListeners(
              event = AppEvent.TimerToggleUp,
              data = getCurrentTime(),
              origin = "[MainClass]"
            )
          }
        }
      } else if (it.key == Key.Escape) {
        uiManager.notifyListeners(
          event = AppEvent.TimerCancel,
          origin = "[MainClass]"
        )
      }
      false
    }
  ) {
    var appMode by remember { mutableStateOf(loadedCurrentMode) }
    var modeWasSelected by remember { mutableStateOf(appMode != AppMode.NotSet) }

    if (!modeWasSelected) {
      Column {
        Text("Choose FLTimer mode:")
        Row {
          TextButton(onClick = { appMode = AppMode.Default; modeWasSelected = true }) {
            Text(AppMode.Default.name)
          }

          TextButton(onClick = { appMode = AppMode.Online; modeWasSelected = true }) {
            Text(AppMode.Online.name)
          }
        }
      }
    } else {
      currentWindowSize = DpSize(800.dp, 400.dp)

      if (appMode == AppMode.Default) {
        LaunchedEffect(true) {
          setupManagers(
            uiManager,
            ScrambleManager(),
            SolvesManager(),
            SessionManager(),
            TimerManager(),
            ConfigurationManager(),
            PersistenceManager()
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