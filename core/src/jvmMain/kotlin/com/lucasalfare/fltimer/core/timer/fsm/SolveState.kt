package com.lucasalfare.fltimer.core.timer.fsm

import kotlinx.coroutines.Job
import com.lucasalfare.fltimer.core.AppEvent
import com.lucasalfare.fltimer.core.getCurrentTime
import com.lucasalfare.fltimer.core.timer.asyncRoutine
import com.lucasalfare.fltimer.core.EventManageable

class SolveState : TimerState {

  private var repeater: Job? = null
  private var start = 0L
  private var elapsed = 0L

  override fun handleInput(inputType: AppEvent, data: Any?): TimerState? {
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
      event = AppEvent.TimerStarted,
      origin = this
    )

    start = t
    repeater = asyncRoutine {
      elapsed = getCurrentTime() - start
      eventManageable.notifyListeners(
        event = AppEvent.TimerUpdate,
        data = elapsed,
        origin = this
      )
    }
  }

  override fun suspendState() {
    repeater!!.cancel()
  }
}