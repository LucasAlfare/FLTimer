package lucasalfare.fltimer.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.configuration.ConfigurationManager
import lucasalfare.fltimer.core.data.SolvesManager
import lucasalfare.fltimer.core.data.persistence.PersistenceManager
import lucasalfare.fltimer.core.data.session.SessionManager
import lucasalfare.fltimer.core.getCurrentTime
import lucasalfare.fltimer.core.scramble.ScrambleManager
import lucasalfare.fltimer.core.setupManagers
import lucasalfare.fltimer.core.timer.TimerManager
import lucasalfare.fltimer.ui.uiManager

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .pointerInput(true) {
            detectTapGestures(onPress = {
              uiManager.notifyListeners(
                event = AppEvent.TimerToggleDown,
                data = getCurrentTime(),
                origin = this
              )
              if (tryAwaitRelease()) {
                uiManager.notifyListeners(
                  event = AppEvent.TimerToggleUp,
                  data = getCurrentTime(),
                  origin = this
                )
              }
            })
          }
      ) {
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
        }

        AndroidApp()
      }
    }
  }
}
