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
import com.lucasalfare.fllistener.setupManagers
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.configuration.ConfigurationsManager
import com.lucasalfare.fltimer.core.getCurrentTime
import com.lucasalfare.fltimer.core.model.data.SolvesManager
import com.lucasalfare.fltimer.core.model.data.persistence.APPLICATION_DATABASE_FILE_NAME
import com.lucasalfare.fltimer.core.model.data.persistence.readAndDefineFLTimerStateFromFile
import com.lucasalfare.fltimer.core.model.data.persistence.writeFLTimerStateToFile
import com.lucasalfare.fltimer.core.model.session.SessionsManager
import com.lucasalfare.fltimer.core.scramble.ScrambleManager
import com.lucasalfare.fltimer.core.timer.TimerManager
import com.lucasalfare.fltimer.ui.FLTimerUiState.Companion.canListenToggling
import com.lucasalfare.fltimer.ui.FLTimerUiState.Companion.timerPressingDown
import com.lucasalfare.fltimer.ui.uiManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path


@OptIn(ExperimentalComposeUiApi::class, ExperimentalUnsignedTypes::class)
fun main() = application {
  readAndDefineFLTimerStateFromFile { File(APPLICATION_DATABASE_FILE_NAME) }

  Window(
    state = WindowState(position = WindowPosition(Alignment.CenterEnd)),
    onCloseRequest = {
      writeFLTimerStateToFile {
        Files.deleteIfExists(Path(APPLICATION_DATABASE_FILE_NAME))
        val targetFile = File(APPLICATION_DATABASE_FILE_NAME)
        targetFile.writeBytes(it.getData().toByteArray())
      }
      exitApplication()
    },
    onKeyEvent = {
      if (it.key == Key.Spacebar) {
        when (it.type) {
          KeyEventType.KeyDown -> {
            if (canListenToggling.value) {
              uiManager.notifyListeners(
                event = FLTimerEvent.TimerToggleDown,
                data = getCurrentTime(),
                origin = "[MainClass]"
              )
            }

            timerPressingDown.value = true
          }

          KeyEventType.KeyUp -> {
            if (canListenToggling.value) {
              uiManager.notifyListeners(
                event = FLTimerEvent.TimerToggleUp,
                data = getCurrentTime(),
                origin = "[MainClass]"
              )
            }

            timerPressingDown.value = false
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
          SessionsManager(),
          ConfigurationsManager(),
          uiManager
        )
      }
    }

    App()
  }
}