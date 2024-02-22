package com.lucasalfare.fltimer.timer

import com.lucasalfare.fllistening.EventManageable
import com.lucasalfare.fltimer.TimerEvent
import com.lucasalfare.fltimer.getCurrentTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private const val LONG_PRESS_TIME = 250

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
    if (event == TimerEvent.TimerToggleInspection) {
      inspectionEnabled = !inspectionEnabled
      currentState =
        if (inspectionEnabled)
          TimerState.ReadyForInspection
        else TimerState.Ready
    }

    if (event == TimerEvent.TimerToggleDown) {
      if (currentState == TimerState.Running) {
        endTime = data as Long
        if (endTime - startTime >= LONG_PRESS_TIME) {
          stopSolve()
        }
      }
    }

    if (event == TimerEvent.TimerToggleUp) {
      if (currentState == TimerState.ReadyForInspection) {
        if (getCurrentTime() - endTime >= 250) {
          startInspection()
        }
      } else if (currentState == TimerState.Ready) {
        if (getCurrentTime() - endTime >= LONG_PRESS_TIME) {
          startTime = data as Long
          startSolve()
        }
      } else if (currentState == TimerState.Finished) {
        notifyListeners(TimerEvent.TimerReady)
        currentState = if (inspectionEnabled) TimerState.ReadyForInspection else TimerState.Ready
      }
    }
  }

  private fun startInspection() {
    if (repeater != null) {
      repeater!!.cancel()
      repeater = null
    }

    currentState = TimerState.Ready

    var counter = 15
    repeater = asyncRoutine(delayTime = 1000L) {
      notifyListeners(event = TimerEvent.TimerInspectionUpdate, data = if (counter > 0) counter-- else 0)
    }
  }

  private fun startSolve() {
    if (repeater != null) {
      repeater!!.cancel()
      repeater = null
    }

    currentState = TimerState.Running

    repeater = asyncRoutine {
      notifyListeners(event = TimerEvent.TimerSolveUpdate, getCurrentTime() - startTime)
    }
  }

  private fun stopSolve() {
    repeater!!.cancel()
    notifyListeners(TimerEvent.TimerFinish, data = endTime - startTime)
    currentState = TimerState.Finished
  }
}

private enum class TimerState {
  ReadyForInspection,
  Ready,
  Running,
  Finished,
}

private val auxCoroutineScope = CoroutineScope(Job())
fun asyncRoutine(
  delayTime: Long = 1L,
  callback: () -> Unit
) = auxCoroutineScope.launch {
  while (true) {
    callback()
    delay(delayTime)
  }
}