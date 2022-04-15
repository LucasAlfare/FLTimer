package lucasalfare.fltimer.android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.configuration.ConfigurationManager
import lucasalfare.fltimer.core.data.SolvesManager
import lucasalfare.fltimer.core.data.session.SessionManager
import lucasalfare.fltimer.core.getCurrentTime
import lucasalfare.fltimer.core.scramble.ScrambleManager
import lucasalfare.fltimer.core.setupManagers
import lucasalfare.fltimer.core.timer.TimerManager
import lucasalfare.fltimer.ui.composables.Display
import lucasalfare.fltimer.ui.uiComponentsManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(true) {
                        detectTapGestures(onPress = {
                            uiComponentsManager.notifyListeners(AppEvent.TimerToggleDown, getCurrentTime())
                            if (tryAwaitRelease()) {
                                uiComponentsManager.notifyListeners(AppEvent.TimerToggleUp, getCurrentTime())
                            }
                        })
                    }
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

                Box(modifier = Modifier.align(Alignment.Center)) {
                    Display()
                }
            }
        }
    }
}
