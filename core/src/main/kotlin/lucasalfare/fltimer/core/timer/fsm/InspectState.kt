package lucasalfare.fltimer.core.timer.fsm

import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.Listenable
import lucasalfare.fltimer.core.data.Penalty
import lucasalfare.fltimer.core.timer.asyncRoutine
import kotlinx.coroutines.Job

class InspectState : TimerState {

  private var repeater: Job? = null
  private var countdown = 15
  private var currentPenalty = Penalty.Ok

  override fun handleInput(inputType: AppEvent, data: Any?): TimerState? {
    if (inputType == InputRelease) {
      suspend()
      return SolveState()
    }
    return null
  }

  override fun update(eventNotifier: Listenable, data: Any?) {
    println("current INSPECTING...")
    repeater = asyncRoutine(delayTime = 1000) {

      currentPenalty = when {
        countdown > 2 -> Penalty.Ok
        countdown in 1..2 -> Penalty.PlusTwo
        else -> Penalty.Dnf
      }

      eventNotifier.notifyListeners(
        AppEvent.TimerInspectionUpdate, arrayOf(countdown, currentPenalty)
      )

      countdown--
    }
  }

  override fun suspend() {
    repeater!!.cancel()
  }
}
