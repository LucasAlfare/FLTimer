@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.lucasalfare.fltimer.desktop

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
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
import com.lucasalfare.fllistener.setupManagers
import kotlinx.coroutines.launch
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.configuration.Config
import com.lucasalfare.fltimer.core.configuration.ConfigurationManager
import com.lucasalfare.fltimer.core.data.SolvesManager
import com.lucasalfare.fltimer.core.data.persistence.PersistenceManager
import com.lucasalfare.fltimer.core.data.persistence.getStartupReader
import com.lucasalfare.fltimer.core.data.session.SessionManager
import com.lucasalfare.fltimer.core.getCurrentTime
import com.lucasalfare.fltimer.core.networking.NetworkManager
import com.lucasalfare.fltimer.core.scramble.ScrambleManager
import com.lucasalfare.fltimer.core.timer.TimerManager
import com.lucasalfare.fltimer.ui.composables.Display
import com.lucasalfare.fltimer.ui.uiManager

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
  // TODO: abstract this?
  val startupReader = getStartupReader()
  val netWorkingModeOn = startupReader.readBoolean(9)
  val askForTimerMode = startupReader.readBoolean(10)

  var currentWindowSize by remember { mutableStateOf(DpSize(400.dp, 200.dp)) }

  Window(
    state = WindowState(
      position = WindowPosition(Alignment.Center),
      size = currentWindowSize
    ),
    onCloseRequest = {
      // Here is assumed that when Compose receive an
      // close request the managers was already set
      uiManager.notifyListeners(FLTimerEvent.ApplicationFinish)
      this.exitApplication()
    },
    onKeyEvent = {
      if (it.key == Key.Spacebar) {
        when (it.type) {
          KeyEventType.KeyDown -> {
            uiManager.notifyListeners(
              event = FLTimerEvent.TimerToggleDown,
              data = getCurrentTime(),
              origin = "[MainClass]"
            )
          }

          KeyEventType.KeyUp -> {
            uiManager.notifyListeners(
              event = FLTimerEvent.TimerToggleUp,
              data = getCurrentTime(),
              origin = "[MainClass]"
            )
          }
        }
      } else if (it.key == Key.Escape) {
        uiManager.notifyListeners(
          event = FLTimerEvent.TimerCancel,
          origin = "[MainClass]"
        )
      }
      false
    }
  ) {
    var onlineMode by remember { mutableStateOf(netWorkingModeOn) }
    var modeWasSelected by remember { mutableStateOf(!askForTimerMode) }

    if (askForTimerMode && !modeWasSelected) {
      DecisionDialog(
        onDefaultCallback = { onlineMode = false; modeWasSelected = true },
        onOnlineCallback = { onlineMode = true; modeWasSelected = true }
      )
    } else {
      if (onlineMode) {
        LaunchedEffect(true) {
          setupManagers(
            uiManager,
            ConfigurationManager(),
            TimerManager(),
            NetworkManager()
          )

          uiManager.notifyListeners(
            event = FLTimerEvent.ConfigSet,
            data = arrayOf(Config.NetworkingModeOn, true),
            origin = this
          )

          currentWindowSize = DpSize(800.dp, 400.dp)
        }

        // show UI
        TestApp()
      } else {
        currentWindowSize = DpSize(800.dp, 400.dp)

        uiManager.notifyListeners(
          event = FLTimerEvent.ConfigSet,
          data = arrayOf(Config.NetworkingModeOn, false),
          origin = this
        )

        LaunchedEffect(true) {
          launch {
            setupManagers(
              PersistenceManager(),
              uiManager,
              ScrambleManager(),
              SolvesManager(),
              SessionManager(),
              TimerManager(),
              ConfigurationManager()
            )
          }
        }

        // show UI
        DefaultDesktopApp()
      }
    }
  }
}

@Composable
fun DecisionDialog(
  onDefaultCallback: () -> Unit = {},
  onOnlineCallback: () -> Unit = {},
  onToggleDialog: () -> Unit = {} // TODO
) {
  Column {
    Text("Select the timer mode:")
    Row {
      Button(onClick = { onDefaultCallback() }) {
        Text("Default")
      }

      Button(enabled = false, onClick = { onOnlineCallback() }) {
        Text("Online")
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