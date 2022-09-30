package lucasalfare.fltimer.core.timer.fsm

import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.EventManageable

class FinishState(private val start: Long) : TimerState {

  override fun handleInput(inputType: AppEvent, data: Any?): TimerState? {
    if (inputType == InputRelease) {
      return ReadyState()
    }
    return null
  }

  override fun update(eventManageable: EventManageable, data: Any?) {
    //diff between values sent by UI is authoritative
    val realElapsed = (data as Long) - start
    eventManageable.notifyListeners(
      event = AppEvent.TimerUpdate,
      data = realElapsed,
      origin = this
    )

    eventManageable.notifyListeners(
      event = AppEvent.TimerFinished,
      data = realElapsed,
      origin = this
    )
  }

  override fun suspendState() {

  }
}
