import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import lucasalfare.fltimer.core.configuration.ConfigurationManager
import lucasalfare.fltimer.core.data.SolvesManager
import lucasalfare.fltimer.core.data.session.SessionManager
import lucasalfare.fltimer.core.scramble.ScrambleManager
import lucasalfare.fltimer.core.setupManagers
import lucasalfare.fltimer.core.timer.TimerManager
import lucasalfare.fltimer.ui.uiComponentsManager

fun main() = application {
  Window(
    state = WindowState(
      width = (720 / 2.5).dp,
      height = (1560 / 2.5).dp,
      position = WindowPosition(Alignment.Center)
    ),
    onCloseRequest = ::exitApplication
  ) {
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

    Text("oi")
  }
}
