package com.lucasalfare.fltimer

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.lucasalfare.fllistener.setupManagers
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.getCurrentTime
import com.lucasalfare.fltimer.core.model.FLTimerModel
import com.lucasalfare.fltimer.core.model.data.SolvesManager
import com.lucasalfare.fltimer.core.model.data.persistence.readAndDefineFLTimerStateFromFile
import com.lucasalfare.fltimer.core.model.data.persistence.writeFLTimerStateToFile
import com.lucasalfare.fltimer.core.scramble.ScrambleManager
import com.lucasalfare.fltimer.core.timer.TimerManager
import com.lucasalfare.fltimer.ui.uiManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // TODO: add custom callback function to load files here in android
    readAndDefineFLTimerStateFromFile()

    FLTimerModel.sessions.first().solves.forEach {
//      println(it)
     Log.d("TRISTEZA", it.toString())
    }

    setContent {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .pointerInput(true) {
            detectTapGestures(onPress = {
              uiManager.notifyListeners(
                event = FLTimerEvent.TimerToggleDown,
                data = getCurrentTime(),
                origin = this
              )
              if (tryAwaitRelease()) {
                uiManager.notifyListeners(
                  event = FLTimerEvent.TimerToggleUp,
                  data = getCurrentTime(),
                  origin = this
                )
              }
            })
          }
      ) {
        LaunchedEffect(true) {
          CoroutineScope(Job()).launch {
            setupManagers(
              uiManager,
              ScrambleManager(),
              SolvesManager(),
              TimerManager()
            )
          }
        }

        AndroidApp()
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    writeFLTimerStateToFile()
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()

    // TODO: add custom callback function to write files here in android
    writeFLTimerStateToFile()
  }
}