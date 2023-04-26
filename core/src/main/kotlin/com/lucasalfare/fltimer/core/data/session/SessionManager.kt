package com.lucasalfare.fltimer.core.data.session

import com.lucasalfare.fllistener.EventManageable
import com.lucasalfare.fltimer.core.FLTimerState

class SessionManager : EventManageable() {

  private val sessions = FLTimerState.getFLTimerState().sessions

  override fun onInitiated() {
    println("[SessionManager] Instance initiated.")
  }

  override fun onNotInitiated() {
    this.initiated = true
  }

  override fun onEvent(event: Any, data: Any?, origin: Any?) {

  }
}