package lucasalfare.fltimer.core.timer.fsm

import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.EventManageable

class ReadyState : TimerState {

  override fun handleInput(inputType: AppEvent, data: Any?): TimerState? {
    if (inputType == InputRelease) {
      if (data != null) {
        val useInspection = data as Boolean
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