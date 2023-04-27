package com.lucasalfare.fltimer.core.data

import com.lucasalfare.fllistener.EventManageable
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.modeling.FLTimerModel
import com.lucasalfare.fltimer.core.modeling.Penalty
import com.lucasalfare.fltimer.core.modeling.Solve

class SolvesManager : EventManageable() {

  private val solvesRef = FLTimerModel.getCurrentActiveSession().solves

  private var tmpTime = 0L
  private var tmpScramble = ""
  private var tmpPenalty = Penalty.Ok

  override fun onInitiated() {
    println("[SolvesManager] Instance initiated.")
  }

  override fun onNotInitiated() {
    this.initiated = true
  }

  override fun onEvent(event: Any, data: Any?, origin: Any?) {
    if (event == FLTimerEvent.TimerFinished) {
      tmpTime = data as Long

      solvesRef += Solve(
        time = tmpTime,
        scramble = tmpScramble,
        penalty = tmpPenalty
      )

      notifyListeners(FLTimerEvent.SolvesUpdate, solvesRef)
    }

    if (event == FLTimerEvent.ScrambleGenerated) {
      val props = data as Array<*>
      tmpScramble = props[1] as String
    }
  }
}