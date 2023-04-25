package com.lucasalfare.fltimer.core.data.session

import com.lucasalfare.fllistener.EventManageable
import com.lucasalfare.fltimer.core.data.FLTimerState

class SessionManager : EventManageable() {

  private val sessions = FLTimerState.getFLTimerState().sessions

  override fun onInitiated() {

  }

  override fun onNotInitiated() {

  }

  override fun onEvent(event: Any, data: Any?, origin: Any?) {

  }
}