package com.lucasalfare.fltimer.core.scramble

import com.lucasalfare.fllistener.EventManageable
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.model.FLTimerModel
import com.lucasalfare.fltimer.core.scramble.scramblers.getFreeRubiksCubeScramble

class ScrambleManager : EventManageable() {

  private var lastScramble = ""
  private var currentScramble = ""

  init {
    genScramble()
  }

  override fun onInitiated() {
    println("[ScrambleManager] Instance initiated.")
  }

  override fun onNotInitiated() {
    notifyListeners(
      event = FLTimerEvent.ScrambleGenerated,
      data = arrayOf(lastScramble, currentScramble),
      origin = this
    )

    FLTimerModel.currentScramble.value = this.currentScramble

    initiated = true
  }

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

        FLTimerModel.currentScramble.value = this.currentScramble
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