package com.lucasalfare.fltimer.ui

import androidx.compose.runtime.mutableStateOf
import com.lucasalfare.fllistener.CallbacksManager

val uiManager = CallbacksManager()

class FLTimerUiState {
  companion object {
    var currentTabName = mutableStateOf("Timer")

    var showingSolves = mutableStateOf(false)

    var timerPressingDown = mutableStateOf(false)

    val canListenToggling = mutableStateOf(true)
  }
}