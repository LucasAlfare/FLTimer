package com.lucasalfare.fltimer.ui

import androidx.compose.runtime.mutableStateOf
import com.lucasalfare.fllistener.CallbacksManager

val uiManager = CallbacksManager()

enum class TabName {
  Timer,
  Solves,
  Stats,
  Configs
}

class FLTimerUiState {
  companion object {
    var currentTabName = mutableStateOf(TabName.Timer.name)
//    var currentTabName = mutableStateOf(TabName.Stats.name)

    var showingSolves = mutableStateOf(false)

    var timerPressingDown = mutableStateOf(false)

    val canListenToggling = mutableStateOf(true)

    val creatingSessionMode = mutableStateOf(false)
  }
}