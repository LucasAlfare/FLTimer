@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package lucasalfare.fltimer.desktop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.getCurrentTime
import lucasalfare.fltimer.core.networking.NetworkManager
import lucasalfare.fltimer.core.setupManagers
import lucasalfare.fltimer.core.timer.TimerManager
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
            uiComponentsManager.notifyListeners(AppEvent.TimerToggleDown, getCurrentTime())
          }

          KeyEventType.KeyUp -> {
            uiComponentsManager.notifyListeners(AppEvent.TimerToggleUp, getCurrentTime())
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
  Box(modifier = Modifier.fillMaxSize()) {
    Box(modifier = Modifier.align(Alignment.Center)) {
      Display()
    }
  }
}