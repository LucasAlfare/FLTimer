package com.lucasalfare.fltimer.ui

import androidx.compose.runtime.mutableStateOf
import com.lucasalfare.fllistener.CallbacksManager

val uiManager = CallbacksManager()

enum class TabName {
  Timer,
  Solves,
  Stats,
  Configs,
  Details
}

// TODO: consider persist UI State as well?
class FLTimerUiState {
  companion object {
    var currentTabName = mutableStateOf(TabName.Timer.name)
//    var currentTabName = mutableStateOf(TabName.Stats.name)

    var showingSolves = mutableStateOf(false)

    var timerPressingDown = mutableStateOf(false)

    val canTimerToggle = mutableStateOf(true)

    val inCreatingSessionMode = mutableStateOf(false)
  }
}