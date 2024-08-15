package com.lucasalfare.fltimer.c_infra.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.type
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.lucasalfare.fltimer.b_usecase.TimerService
import com.lucasalfare.fltimer.toTimestamp
import kotlinx.coroutines.launch

class ComposeWindowLauncher(private var timerService: TimerService) {

  fun launch() = application {
    val coroutineScope = rememberCoroutineScope()

    Window(
      onCloseRequest = { exitApplication() },
      state = WindowState(position = WindowPosition(Alignment.Center)),
      onKeyEvent = {
        when (it.type) {
          KeyEventType.KeyDown -> {
            coroutineScope.launch { timerService.onToggleDown() }
          }

          KeyEventType.KeyUp -> {
            coroutineScope.launch { timerService.onToggleUp() }
          }

          else -> {}
        }

        true
      }
    ) {
      Column {
        Text(
          timerService.timer.appDataState.timerCurrentCountingTime.toTimestamp(),
        )

        LazyColumn {
          items(timerService.timer.appDataState.allSolves) {
            Text(it.time.toTimestamp())
          }
        }
      }
    }
  }
}