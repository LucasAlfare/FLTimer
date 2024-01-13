package com.lucasalfare.fltimer.scramble

import com.lucasalfare.fllistening.EventManageable
import com.lucasalfare.fltimer.Event
import com.lucasalfare.fltimer.scramble.scramblers.getFreeRubiksCubeScramble

class ScrambleManager : EventManageable() {

  private var lastScramble = ""
  private var currentScramble = ""

  override suspend fun initialize() {
    notifyListeners(
      event = Event.ScrambleGenerated,
      data = arrayOf(lastScramble, currentScramble)
    )

//    FLTimerState.currentScramble.value = this.currentScramble

    initialized = true
  }

  init {
    genScramble()
  }

  override fun onEvent(event: Any, data: Any?) {
    when (event) {
      Event.RequestScrambleGenerated -> {
        notifyListeners(
          event = Event.ScrambleGenerated,
          data = arrayOf(lastScramble, currentScramble)
        )
      }

      Event.TimerFinish -> {
        genScramble()
      }

      Event.TimerPreReady -> { //only propagates the scrambles when timer says READY
        notifyListeners(
          event = Event.ScrambleGenerated,
          data = arrayOf(lastScramble, currentScramble)
        )

//        FLTimerState.currentScramble.value = this.currentScramble
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