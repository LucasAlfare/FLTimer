package com.lucasalfare.fltimer.a_domain

import com.lucasalfare.fltimer.a_domain.models.Penalty
import com.lucasalfare.fltimer.a_domain.models.Solve

interface AppDataState {

  var timerState: TimerState
  var timerCurrentCountingTime: Long
  var startInspectionMoment: Long
  var stopInspectionMoment: Long
  var startMoment: Long
  var stopMoment: Long
  var scramble: String
  var penalty: Penalty
  var comment: String
  var allSolves: List<Solve>
}