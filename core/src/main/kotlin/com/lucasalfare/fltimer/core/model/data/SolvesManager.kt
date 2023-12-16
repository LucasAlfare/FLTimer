package com.lucasalfare.fltimer.core.model.data

import com.lucasalfare.fllistening.EventManageable
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.model.FLTimerState
import com.lucasalfare.fltimer.core.model.Penalty
import com.lucasalfare.fltimer.core.model.Solve

class SolvesManager : EventManageable() {

  private var solvesRef = FLTimerState.getCurrentActiveSession().solves

  private var tmpTime = 0L
  private var tmpScramble = ""
  private var tmpPenalty = Penalty.Ok

  override suspend fun initialize() {
    initialized = true
  }

  override fun onEvent(event: Any, data: Any?) {
    if (event == FLTimerEvent.TimerFinished) {
      tmpTime = data as Long

      solvesRef += Solve(
        time = tmpTime,
        scramble = tmpScramble,
        penalty = tmpPenalty
      )
    }

    if (event == FLTimerEvent.SolveItemPenaltyUpdate) {
      val props = data as Array<*>
      val targetSolve = props[0] as Solve
      val nextPenalty = props[1] as Penalty
      targetSolve.penalty = nextPenalty
    }

    if (event == FLTimerEvent.SolvesItemRemove) {
      val solveTOBeRemoved = data as Solve
      solvesRef.remove(solveTOBeRemoved)
    }

    if (event == FLTimerEvent.ScrambleGenerated) {
      val props = data as Array<*>
      tmpScramble = props[1] as String
    }

    if (event == FLTimerEvent.SessionsUpdate) {
      solvesRef = FLTimerState.getCurrentActiveSession().solves
    }

    if (event == FLTimerEvent.SolvesClear) {
      solvesRef.clear()
    }
  }
}