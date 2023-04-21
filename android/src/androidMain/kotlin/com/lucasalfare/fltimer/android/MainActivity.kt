package com.lucasalfare.fltimer.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.lucasalfare.fltimer.core.AppEvent
import com.lucasalfare.fltimer.core.configuration.ConfigurationManager
import com.lucasalfare.fltimer.core.data.SolvesManager
import com.lucasalfare.fltimer.core.data.persistence.PersistenceManager
import com.lucasalfare.fltimer.core.data.session.SessionManager
import com.lucasalfare.fltimer.core.getCurrentTime
import com.lucasalfare.fltimer.core.scramble.ScrambleManager
import com.lucasalfare.fltimer.core.setupManagers
import com.lucasalfare.fltimer.core.timer.TimerManager
import com.lucasalfare.fltimer.ui.uiManager
import comlucasalfare.fltimer.android.AndroidApp

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