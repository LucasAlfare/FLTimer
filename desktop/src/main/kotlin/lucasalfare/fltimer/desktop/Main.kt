package lucasalfare.fltimer.desktop

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.configuration.ConfigurationManager
import lucasalfare.fltimer.core.data.SolvesManager
import lucasalfare.fltimer.core.data.session.SessionManager
import lucasalfare.fltimer.core.getCurrentTime
import lucasalfare.fltimer.core.scramble.ScrambleManager
import lucasalfare.fltimer.core.setupManagers
import lucasalfare.fltimer.core.timer.TimerManager
import lucasalfare.fltimer.ui.composables.FullScreenHandleableApplication
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
      }
      false
    },
    onCloseRequest = ::exitApplication
  ) {
    // before show UI, sets managers up
    LaunchedEffect(true) {
      setupManagers(
        uiComponentsManager,
        ScrambleManager(),
        SolvesManager(),
        SessionManager(),
        TimerManager(),
        ConfigurationManager()
      )
    }

    // ...then show UI
    FullScreenHandleableApplication {
      DesktopApp()
    }
  }
}
