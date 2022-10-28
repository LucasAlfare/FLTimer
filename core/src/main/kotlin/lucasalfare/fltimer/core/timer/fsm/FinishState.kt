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

  @Suppress("UNCHECKED_CAST")
  override fun update(eventManageable: EventManageable, data: Any?) {
    val props = data as Array<*>
    val t = props[0] as Long
    val callback = props[1] as () -> Unit

    //diff between values sent by UI is authoritative
    val realElapsed = t - start
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

    callback()
  }

  override fun suspendState() {

  }
}
