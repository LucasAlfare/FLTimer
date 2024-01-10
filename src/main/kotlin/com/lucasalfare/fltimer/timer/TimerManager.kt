package com.lucasalfare.fltimer.timer

import com.lucasalfare.fllistening.EventManageable
import com.lucasalfare.fltimer.Event
import com.lucasalfare.fltimer.getCurrentTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerManager : EventManageable() {

  private var repeater: Job? = null
  private var inspectionEnabled = false
  private var currentState = TimerState.Ready
  private var startTime = 0L
  private var endTime = 0L

  override suspend fun initialize() {
    initialized = true
  }

  override fun onEvent(event: Any, data: Any?) {
    if (event == Event.TimerToggleInspection) {
      inspectionEnabled = !inspectionEnabled
      currentState =
        if (inspectionEnabled)
          TimerState.ReadyForInspection
        else TimerState.Ready
    }

    if (event == Event.TimerToggleDown) {
      if (currentState == TimerState.Running) {
        endTime = data as Long
        if (endTime - startTime >= 250) {
          stopSolve()
        }
      }
    }

    if (event == Event.TimerToggleUp) {
      if (currentState == TimerState.ReadyForInspection) {
        if (getCurrentTime() - endTime >= 250) {
          startInspection()
          currentState = TimerState.Ready
        }
      } else if (currentState == TimerState.Ready) {
        if (getCurrentTime() - endTime >= 250) {
          startTime = data as Long
          startSolve()
          currentState = TimerState.Running
        }
      } else if (currentState == TimerState.Finished) {
        currentState = if (inspectionEnabled) TimerState.ReadyForInspection else TimerState.Ready
      }
    }
  }

  private fun startInspection() {
    if (repeater != null) {
      repeater!!.cancel()
      repeater = null
    }

    var counter = 15
    repeater = asyncRoutine(delayTime = 1000L) {
      if (counter >= 0) {
        notifyListeners(event = Event.TimerInspectionUpdate, data = counter--)
      } else {
        // notify DNF penalty...
      }
    }
  }

  private fun startSolve() {
    if (repeater != null) {
      repeater!!.cancel()
      repeater = null
    }

    repeater = asyncRoutine {
      notifyListeners(event = Event.TimerSolveUpdate, getCurrentTime() - startTime)
    }
  }

  private fun stopSolve() {
    repeater!!.cancel()
    notifyListeners(Event.TimerFinish, data = endTime - startTime)
    currentState = TimerState.Finished
  }
}

private enum class TimerState {
  ReadyForInspection,
  Ready,
  Running,
  Finished,
}

private val AuxCoroutineScope = CoroutineScope(Job())
fun asyncRoutine(
  delayTime: Long = 1L,
  callback: () -> Unit
) = AuxCoroutineScope.launch {
  while (true) {
    callback()
    delay(delayTime)
  }
}