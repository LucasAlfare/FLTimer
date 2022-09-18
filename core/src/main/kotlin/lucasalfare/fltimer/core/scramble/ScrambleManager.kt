@file:Suppress("NON_EXHAUSTIVE_WHEN")

package lucasalfare.fltimer.core.scramble

import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.EventManageable
import lucasalfare.fltimer.core.scramble.scramblers.getFreeRubiksCubeScramble

class ScrambleManager : EventManageable() {

  private var lastScramble = ""
  private var currentScramble = ""

  override fun init() {
    genScramble()
    notifyListeners(
      AppEvent.ScrambleGenerated,
      arrayOf(lastScramble, currentScramble)
    )
  }

  override fun onEvent(event: AppEvent, data: Any?) {
    when (event) {
      AppEvent.RequestScrambleGenerated -> {
        notifyListeners(
          AppEvent.ScrambleGenerated,
          arrayOf(lastScramble, currentScramble)
        )
      }

      AppEvent.TimerFinished -> {
        genScramble()
      }

      AppEvent.TimerReady -> { //only propagates the scrambles when timer says READY
        notifyListeners(
          AppEvent.ScrambleGenerated,
          arrayOf(lastScramble, currentScramble)
        )
      }
    }
  }

  private fun genScramble() {
    lastScramble = currentScramble
    currentScramble = getFreeRubiksCubeScramble()
  }
}
