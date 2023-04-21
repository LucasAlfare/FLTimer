package com.lucasalfare.fltimer.core.timer.fsm

import com.lucasalfare.fltimer.core.AppEvent
import com.lucasalfare.fltimer.core.EventManageable

class ReadyState : TimerState {

  override fun handleInput(inputType: AppEvent, data: Any?): TimerState? {
    if (inputType == InputRelease) {
      if (data != null) {
        val props = (data as BooleanArray)
        val networkingModeOn = props[1]
        val networkingCanStart = props[2]

        if (networkingModeOn && !networkingCanStart) {
          return null
        }

        val useInspection = props[0]
        return if (useInspection) InspectState() else SolveState()
      }
    }
    return null
  }

  override fun update(eventManageable: EventManageable, data: Any?) {
    eventManageable.notifyListeners(event = AppEvent.TimerReady, origin = this)
  }

  override fun suspendState() {

  }
}