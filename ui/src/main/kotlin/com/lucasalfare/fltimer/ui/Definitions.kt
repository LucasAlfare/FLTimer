package com.lucasalfare.fltimer.ui

import androidx.compose.runtime.mutableStateOf
import com.lucasalfare.fllistener.CallbacksManager

val uiManager = CallbacksManager()

enum class TabName(name: String) {
  Timer("Timer"),
  Solves("Solves"),
  Stats("Stats"),
  Configs("Configs")
}

class FLTimerUiState {
  companion object {
    var currentTabName = mutableStateOf(TabName.Timer.name)

    var showingSolves = mutableStateOf(false)

    var timerPressingDown = mutableStateOf(false)

    val canListenToggling = mutableStateOf(true)
  }
}