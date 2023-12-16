package com.lucasalfare.fltimer.ui

import androidx.compose.runtime.mutableStateOf
import com.lucasalfare.fllistening.EventManageable

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

class CallbacksManager : EventManageable() {

  private val callbacks = mutableListOf<(Any, Any?) -> Unit>()

  override suspend fun initialize() {
    initialized = true
  }

  override fun onEvent(event: Any, data: Any?) {
    callbacks.forEach { callback ->
      callback(event, data)
    }
  }

  fun addCallback(callback: (Any, Any?) -> Unit): (Any, Any?) -> Unit {
    if (!callbacks.contains(callback)) callbacks.add(callback)
    return callback
  }

  fun removeCallback(callback: (Any, Any?) -> Unit) {
    callbacks.remove(callback)
  }
}