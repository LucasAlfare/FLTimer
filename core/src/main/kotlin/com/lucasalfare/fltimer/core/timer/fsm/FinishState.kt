package com.lucasalfare.fltimer.core.timer.fsm

import com.lucasalfare.fllistening.EventManageable
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.model.FLTimerState
import com.lucasalfare.fltimer.core.toTimestamp

class FinishState(private val start: Long) : TimerState {

  override fun handleInput(inputType: FLTimerEvent, data: Any?): TimerState? {
    if (inputType == InputRelease) {
      return ReadyState()
    }
    return null
  }

  @Suppress("UNCHECKED_CAST")
  override fun update(eventManageable: EventManageable, data: Any?) {
    val props = data as Array<*>
    val toggleTime = props[0] as Long
    val callback = props[1] as () -> Unit

    //diff between values sent by UI is authoritative
    val realElapsed = toggleTime - start
    eventManageable.notifyListeners(
      event = FLTimerEvent.TimerUpdate,
      data = realElapsed
    )

    eventManageable.notifyListeners(
      event = FLTimerEvent.TimerFinished,
      data = realElapsed
    )

    callback()

    FLTimerState.currentDisplayValue.value = realElapsed.toTimestamp()
  }

  override fun suspendState() {

  }
}
