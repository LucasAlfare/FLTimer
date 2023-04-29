package com.lucasalfare.fltimer.core.model.session

import com.lucasalfare.fllistener.EventManageable
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.model.FLTimerState

class SessionsManager : EventManageable() {

  override fun onInitiated() {

  }

  override fun onNotInitiated() {

  }

  override fun onEvent(event: Any, data: Any?, origin: Any?) {
    if (event == FLTimerEvent.SessionSwitch) {
      val targetNextSession = data as Session
      FLTimerState.currentActiveSessionName.value = targetNextSession.name
      notifyListeners(FLTimerEvent.SessionsUpdate)
    }
  }
}