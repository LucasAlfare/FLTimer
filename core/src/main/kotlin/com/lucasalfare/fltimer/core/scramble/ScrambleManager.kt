package com.lucasalfare.fltimer.core.scramble

import com.lucasalfare.fllistening.EventManageable
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.model.FLTimerState
import com.lucasalfare.fltimer.core.scramble.scramblers.getFreeRubiksCubeScramble

class ScrambleManager : EventManageable() {

  private var lastScramble = ""
  private var currentScramble = ""

  init {
    genScramble()
  }

  override suspend fun initialize() {
    notifyListeners(
      event = FLTimerEvent.ScrambleGenerated,
      data = arrayOf(lastScramble, currentScramble)
    )

    FLTimerState.currentScramble.value = this.currentScramble

    initialized = true
  }

  override fun onEvent(event: Any, data: Any?) {
    when (event) {
      FLTimerEvent.RequestScrambleGenerated -> {
        notifyListeners(
          event = FLTimerEvent.ScrambleGenerated,
          data = arrayOf(lastScramble, currentScramble)
        )
      }

      FLTimerEvent.TimerFinished -> {
        genScramble()
      }

      FLTimerEvent.TimerReady -> { //only propagates the scrambles when timer says READY
        notifyListeners(
          event = FLTimerEvent.ScrambleGenerated,
          data = arrayOf(lastScramble, currentScramble)
        )

        FLTimerState.currentScramble.value = this.currentScramble
      }

      else -> {}
    }
  }

  private fun genScramble() {
    lastScramble = currentScramble
    currentScramble =
            // ALWAYS GET SEQUENCE HERE...
      getFreeRubiksCubeScramble()
  }
}