@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package lucasalfare.fltimer.desktop

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.window.*
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.configuration.ConfigurationManager
import lucasalfare.fltimer.core.data.SolvesManager
import lucasalfare.fltimer.core.data.session.SessionManager
import lucasalfare.fltimer.core.getCurrentTime
import lucasalfare.fltimer.core.scramble.ScrambleManager
import lucasalfare.fltimer.core.setupManagers
import lucasalfare.fltimer.core.timer.TimerManager
import lucasalfare.fltimer.ui.composables.*
import lucasalfare.fltimer.ui.composables.online.UsersPanel
import lucasalfare.fltimer.ui.uiManager

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
            uiManager.notifyListeners(
              event = AppEvent.TimerToggleDown,
              data = getCurrentTime(),
              origin = this
            )
          }

          KeyEventType.KeyUp -> {
            uiManager.notifyListeners(
              event = AppEvent.TimerToggleUp,
              data = getCurrentTime(),
              origin = this
            )
          }
        }
      } else if (it.key == Key.Escape) {
        uiManager.notifyListeners(event = AppEvent.TimerCancel, origin = this)
      }
      false
    },
    onCloseRequest = ::exitApplication
  ) {
    // before show UI, sets managers up
    LaunchedEffect(true) {
      setupManagers(
        uiManager,
        ScrambleManager(),
        SolvesManager(),
        SessionManager(),
        TimerManager(),
        ConfigurationManager()
      )
    }

    DefaultDesktopApp()

    /*
    // ...then show UI
    FullScreenHandleableApplication {
      DefaultDesktopApp()
    }
     */
  }
}
