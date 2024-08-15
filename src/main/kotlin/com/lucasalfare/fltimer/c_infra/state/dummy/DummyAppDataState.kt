package com.lucasalfare.fltimer.c_infra.state.dummy

import com.lucasalfare.fltimer.a_domain.AppDataState
import com.lucasalfare.fltimer.a_domain.TimerState
import com.lucasalfare.fltimer.a_domain.models.Penalty
import com.lucasalfare.fltimer.a_domain.models.Solve

object DummyAppDataState : AppDataState {
  override var timerState: TimerState = TimerState.Ready
  override var timerCurrentCountingTime: Long = 0L
  override var startInspectionMoment: Long = 0L
  override var stopInspectionMoment: Long = 0L
  override var startMoment: Long = 0L
  override var stopMoment: Long = 0L
  override var scramble: String = ""
  override var penalty: Penalty = Penalty.Ok
  override var comment: String = ""
  override var allSolves: List<Solve> = mutableListOf()
}