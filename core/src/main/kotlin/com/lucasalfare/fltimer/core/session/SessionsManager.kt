package com.lucasalfare.fltimer.core.session

import com.lucasalfare.fllistener.EventManageable
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.model.FLTimerModel
import com.lucasalfare.fltimer.core.model.Session

class SessionsManager : EventManageable() {

  override fun onInitiated() {

  }

  override fun onNotInitiated() {

  }

  override fun onEvent(event: Any, data: Any?, origin: Any?) {
    if (event == FLTimerEvent.SessionSwitch) {
      val targetNextSession = data as Session
      FLTimerModel.currentActiveSessionName.value = targetNextSession.name
      notifyListeners(FLTimerEvent.SessionsUpdate)
    }
  }
}