@file:Suppress("NON_EXHAUSTIVE_WHEN")

package com.lucasalfare.fltimer.core.scramble

import com.lucasalfare.fltimer.core.AppEvent
import com.lucasalfare.fltimer.core.EventManageable
import com.lucasalfare.fltimer.core.scramble.scramblers.getFreeRubiksCubeScramble

class ScrambleManager : EventManageable() {

  private var lastScramble = ""
  private var currentScramble = ""

  override fun init() {
    genScramble()
    notifyListeners(
      event = AppEvent.ScrambleGenerated,
      data = arrayOf(lastScramble, currentScramble),
      origin = this
    )

    initiated = true
  }

  override fun onEvent(event: AppEvent, data: Any?, origin: Any?) {
    when (event) {
      AppEvent.RequestScrambleGenerated -> {
        notifyListeners(
          event = AppEvent.ScrambleGenerated,
          data = arrayOf(lastScramble, currentScramble),
          origin = this
        )
      }

      AppEvent.TimerFinished -> {
        genScramble()
      }

      AppEvent.TimerReady -> { //only propagates the scrambles when timer says READY
        notifyListeners(
          event = AppEvent.ScrambleGenerated,
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
}
