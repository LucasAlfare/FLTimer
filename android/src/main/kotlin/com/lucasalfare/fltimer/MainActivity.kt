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
import com.lucasalfare.fltimer.core.model.data.SolvesManager
import com.lucasalfare.fltimer.core.model.data.persistence.APPLICATION_DATABASE_FILE_NAME
import com.lucasalfare.fltimer.core.model.data.persistence.readAndDefineFLTimerStateFromFile
import com.lucasalfare.fltimer.core.model.data.persistence.writeFLTimerStateToFile
import com.lucasalfare.fltimer.core.model.session.SessionsManager
import com.lucasalfare.fltimer.core.scramble.ScrambleManager
import com.lucasalfare.fltimer.core.timer.TimerManager
import com.lucasalfare.fltimer.ui.uiManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    readAndDefineFLTimerStateFromFile {
      val path = applicationContext.filesDir
      Log.d("FLTimer", "Timer data file is properly loaded from internal storage.")
      return@readAndDefineFLTimerStateFromFile File(
        path,
        APPLICATION_DATABASE_FILE_NAME
      )
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
              TimerManager(),
              SessionsManager()
            )
          }
        }

        AndroidApp()
      }
    }
  }

  @OptIn(ExperimentalUnsignedTypes::class)
  override fun onPause() {
    super.onPause()
    writeFLTimerStateToFile {
      val path = applicationContext.filesDir
      var file = File(path, APPLICATION_DATABASE_FILE_NAME)
      Files.deleteIfExists(file.toPath())
      file = File(path, APPLICATION_DATABASE_FILE_NAME)
      val stream = FileOutputStream(file)
      stream.write(it.getData().toByteArray())
      Log.d("FLTimer", "Timer data file properly saved on internal storage.")
    }
  }
}