package com.lucasalfare.fltimer.core.timer.fsm

import com.lucasalfare.fllistening.EventManageable
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.model.FLTimerState
import com.lucasalfare.fltimer.core.model.Penalty
import com.lucasalfare.fltimer.core.timer.asyncRoutine
import kotlinx.coroutines.Job

class InspectState : TimerState {

  private var repeater: Job? = null
  private var countdown = 15
  private var currentPenalty = Penalty.Ok

  override fun handleInput(inputType: FLTimerEvent, data: Any?): TimerState? {
    if (inputType == InputRelease) {
      suspendState()
      return SolveState()
    }
    return null
  }

  override fun update(eventManageable: EventManageable, data: Any?) {
    repeater = asyncRoutine(delayTime = 1000) {

      currentPenalty = when {
        countdown > 2 -> Penalty.Ok
        countdown in 1..2 -> Penalty.PlusTwo
        else -> Penalty.Dnf
      }

      eventManageable.notifyListeners(
        event = FLTimerEvent.TimerInspectionUpdate,
        data = arrayOf(countdown, currentPenalty)
      )

      FLTimerState.currentDisplayValue.value = "$countdown"

      countdown--
    }
  }

  override fun suspendState() {
    repeater!!.cancel()
  }
}
