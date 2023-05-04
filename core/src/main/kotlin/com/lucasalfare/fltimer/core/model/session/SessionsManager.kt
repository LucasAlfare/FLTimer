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
    if (event == FLTimerEvent.SessionCreate) {
      val newSession = data as String
      FLTimerState.sessions += Session(name = newSession)
    }

    if (event == FLTimerEvent.SessionSwitch) {
      if (data is Session) {
        val targetNextSession = data
        FLTimerState.currentActiveSessionName.value = targetNextSession.name
        notifyListeners(FLTimerEvent.SessionsUpdate)
      } else {
        val targetNextSessionName = data as String
        FLTimerState.currentActiveSessionName.value = targetNextSessionName
        notifyListeners(FLTimerEvent.SessionsUpdate)
      }
    }
  }
}