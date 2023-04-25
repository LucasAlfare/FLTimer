package com.lucasalfare.fltimer.core.timer

import com.lucasalfare.fllistener.EventManageable
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.timer.fsm.ReadyState
import com.lucasalfare.fltimer.core.timer.fsm.TimerState

class TimerManager : EventManageable() {

  private var currentState: TimerState = ReadyState()

  override fun onInitiated() {
  }

  override fun onNotInitiated() {
    initiated = true
  }

  override fun onEvent(event: Any, data: Any?, origin: Any?) {
    when (event) {
      FLTimerEvent.TimerToggleDown, FLTimerEvent.TimerToggleUp -> {
        val nextState: TimerState? = currentState.handleInput(
          event as FLTimerEvent,
          booleanArrayOf(
            false,
            false,
            true
          )
        )

        if (nextState != null) {
          currentState = nextState
          currentState.update(
            eventManageable = this,
            data = arrayOf(
              data as Long,
              fun() { }
            )
          )
        }

        notifyListeners(event = event, data = data)
      }

      else -> {}
    }
  }
}
