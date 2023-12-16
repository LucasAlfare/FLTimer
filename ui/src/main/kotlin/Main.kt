import androidx.compose.ui.Alignment
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.getCurrentTime
import com.lucasalfare.fltimer.ui.FLTimerUiState.Companion.canTimerToggle
import com.lucasalfare.fltimer.ui.FLTimerUiState.Companion.timerPressingDown
import com.lucasalfare.fltimer.ui.theme.MyFontFamilies
import com.lucasalfare.fltimer.ui.uiManager


fun main() = application {
//  readAndDefineFLTimerStateFromFile { File(APPLICATION_DATABASE_FILE_NAME) }
  defineMyFontFamilies()

  Window(
    state = WindowState(position = WindowPosition(Alignment.Center)),
    onCloseRequest = {
//      writeFLTimerStateToFile {
//        Files.deleteIfExists(Path(APPLICATION_DATABASE_FILE_NAME))
//        val targetFile = File(APPLICATION_DATABASE_FILE_NAME)
//        targetFile.writeBytes(it.getData().toByteArray())
//      }
      exitApplication()
    },
    onKeyEvent = {
      if (it.key == Key.Spacebar) {
        when (it.type) {
          KeyEventType.KeyDown -> {
            if (canTimerToggle.value) {
              uiManager.notifyListeners(
                event = FLTimerEvent.TimerToggleDown,
                data = getCurrentTime()
              )
            }

            timerPressingDown.value = true
          }

          KeyEventType.KeyUp -> {
            if (canTimerToggle.value) {
              uiManager.notifyListeners(
                event = FLTimerEvent.TimerToggleUp,
                data = getCurrentTime()
              )
            }

            timerPressingDown.value = false
          }
        }
      } else if (it.key == Key.Escape) {
        uiManager.notifyListeners(event = FLTimerEvent.TimerCancel)
      }

      false
    }
  ) {
    // TODO: slower computers may not properly init the managers asynchronously?
//    LaunchedEffect(Unit) {
//      CoroutineScope(Job()).launch {
//        setupManagers(
//          TimerManager(),
//          ScrambleManager(),
//          SolvesManager(),
//          SessionsManager(),
//          ConfigurationsManager(),
//          uiManager
//        )
//      }
//    }

    App()
  }
}

/**
 * Method used to supply font families to the theme,
 * using files of the current module.
 */
private fun defineMyFontFamilies() {
  MyFontFamilies.defineRegular {
    FontFamily(
      Font(
        resource = "fonts/JetBrainsMono-Regular.ttf"
      )
    )
  }

  MyFontFamilies.defineBold {
    FontFamily(
      Font(
        resource = "fonts/JetBrainsMono-Bold.ttf"
      )
    )
  }

  MyFontFamilies.defineItalic {
    FontFamily(
      Font(
        resource = "fonts/JetBrainsMono-Italic.ttf"
      )
    )
  }
}