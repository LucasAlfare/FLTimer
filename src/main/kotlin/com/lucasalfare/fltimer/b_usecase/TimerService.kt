package com.lucasalfare.fltimer.b_usecase

import com.lucasalfare.fltimer.a_domain.Timer
import com.lucasalfare.fltimer.a_domain.TimerState

class TimerService(
  var timer: Timer,
  var onTimerFinish: suspend () -> Unit = {}
) {

  suspend fun onToggleDown(moment: Long = System.currentTimeMillis()) {
    when (timer.appDataState.timerState) {
      TimerState.Running -> {
        timer.appDataState.stopMoment = moment
        timer.stopTimer()
        timer.appDataState.timerState = TimerState.Finished
      }

      else -> {}
    }
  }

  suspend fun onToggleUp(moment: Long = System.currentTimeMillis()) {
    when (timer.appDataState.timerState) {
      TimerState.Ready -> {
        timer.appDataState.stopInspectionMoment = moment
        timer.appDataState.startMoment = moment

        timer.stopInspection()
        timer.startTimer()

        timer.appDataState.timerState = TimerState.Running
      }

      TimerState.Finished -> {
        onTimerFinish()
        timer.appDataState.timerState = TimerState.Ready
      }

      else -> {}
    }
  }
}
