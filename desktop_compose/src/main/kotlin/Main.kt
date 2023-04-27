import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.lucasalfare.fllistener.CallbacksManager
import com.lucasalfare.fllistener.setupManagers
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.configuration.ConfigurationsManager
import com.lucasalfare.fltimer.core.data.SolvesManager
import com.lucasalfare.fltimer.core.data.persistence.writeFLTimerStateToFile
import com.lucasalfare.fltimer.core.getCurrentTime
import com.lucasalfare.fltimer.core.scramble.ScrambleManager
import com.lucasalfare.fltimer.core.timer.TimerManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

val uiManager = CallbacksManager()

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
  Window(
    state = WindowState(position = WindowPosition(Alignment.CenterEnd)),
    onCloseRequest = {
      writeFLTimerStateToFile()
      exitApplication()
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
            println("keyUp")
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
    // TODO: slower computers may not properly init the managers assynchrnously
    LaunchedEffect(Unit) {
      CoroutineScope(Job()).launch {
        setupManagers(
          TimerManager(),
          ScrambleManager(),
          SolvesManager(),
          uiManager
        )
      }
    }

    App()
  }
}