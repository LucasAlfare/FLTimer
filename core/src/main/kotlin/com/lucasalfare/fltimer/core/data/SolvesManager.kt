package com.lucasalfare.fltimer.core.data

import com.lucasalfare.fllistener.EventManageable
import com.lucasalfare.fltimer.core.FLTimerEvent

class SolvesManager : EventManageable() {

  private val currSolvesRef = FLTimerState.getCurrentActiveSession().solves

  private var tmpTime = 0L
  private var tmpScramble = ""
  private var tmpPenalty = Penalty.Ok

  override fun onInitiated() {
//    println("[SolvesManager] Instance initiated.")
  }

  override fun onNotInitiated() {
    this.initiated = true
  }

  override fun onEvent(event: Any, data: Any?, origin: Any?) {
    if (event == FLTimerEvent.TimerFinished) {
      tmpTime = data as Long

      val nextSolve = Solve(
        time = tmpTime,
        scramble = tmpScramble,
        penalty = tmpPenalty
      )

      currSolvesRef += nextSolve

      // TODO: should notify or UI watch the solves ref to auto-update? Only work with compose
      notifyListeners(FLTimerEvent.SolvesUpdate, currSolvesRef)
    }
  }
}