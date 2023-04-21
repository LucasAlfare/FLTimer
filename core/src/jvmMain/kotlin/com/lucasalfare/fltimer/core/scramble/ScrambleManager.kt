@file:Suppress("NON_EXHAUSTIVE_WHEN")

package com.lucasalfare.fltimer.core.scramble

import com.lucasalfare.fllistener.EventManageable
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.scramble.scramblers.getFreeRubiksCubeScramble

class ScrambleManager : EventManageable() {

  private var lastScramble = ""
  private var currentScramble = ""

  override fun onEvent(event: Any, data: Any?, origin: Any?) {
    when (event) {
      FLTimerEvent.RequestScrambleGenerated -> {
        notifyListeners(
          event = FLTimerEvent.ScrambleGenerated,
          data = arrayOf(lastScramble, currentScramble),
          origin = this
        )
      }

      FLTimerEvent.TimerFinished -> {
        genScramble()
      }

      FLTimerEvent.TimerReady -> { //only propagates the scrambles when timer says READY
        notifyListeners(
          event = FLTimerEvent.ScrambleGenerated,
          data = arrayOf(lastScramble, currentScramble),
          origin = this
        )
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

  override fun onInitiated() {
    genScramble()
    notifyListeners(
      event = FLTimerEvent.ScrambleGenerated,
      data = arrayOf(lastScramble, currentScramble),
      origin = this
    )

    initiated = true
  }

  override fun onNotInitiated() {

  }
}
