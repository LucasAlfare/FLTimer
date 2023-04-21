package com.lucasalfare.fltimer.core.timer.fsm

import com.lucasalfare.fllistener.EventManageable
import kotlinx.coroutines.Job
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.getCurrentTime
import com.lucasalfare.fltimer.core.timer.asyncRoutine

class SolveState : TimerState {

  private var repeater: Job? = null
  private var start = 0L
  private var elapsed = 0L

  override fun handleInput(inputType: FLTimerEvent, data: Any?): TimerState? {
    if (inputType == InputPress) {
      suspendState()
      return FinishState(start)
    }
    return null
  }

  override fun update(eventManageable: EventManageable, data: Any?) {
    val props = data as Array<*>
    val t = props[0] as Long

    eventManageable.notifyListeners(
      event = FLTimerEvent.TimerStarted,
      origin = this
    )

    start = t
    repeater = asyncRoutine {
      elapsed = getCurrentTime() - start
      eventManageable.notifyListeners(
        event = FLTimerEvent.TimerUpdate,
        data = elapsed,
        origin = this
      )
    }
  }

  override fun suspendState() {
    repeater!!.cancel()
  }
}