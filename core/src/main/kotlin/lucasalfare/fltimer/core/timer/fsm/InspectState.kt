package lucasalfare.fltimer.core.timer.fsm

import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.data.Penalty
import lucasalfare.fltimer.core.timer.asyncRoutine
import kotlinx.coroutines.Job
import lucasalfare.fltimer.core.EventManageable
import lucasalfare.fltimer.core.L

class InspectState : TimerState {

  private var repeater: Job? = null
  private var countdown = 15
  private var currentPenalty = Penalty.Ok

  val logger = L()

  init {
    logger.logAllowed = false
  }

  override fun handleInput(inputType: AppEvent, data: Any?): TimerState? {
    if (inputType == InputRelease) {
      suspendState()
      return SolveState()
    }
    return null
  }

  override fun update(eventNotifier: EventManageable, data: Any?) {
    logger.d("current INSPECTING...")
    repeater = asyncRoutine(delayTime = 1000) {

      currentPenalty = when {
        countdown > 2 -> Penalty.Ok
        countdown in 1..2 -> Penalty.PlusTwo
        else -> Penalty.Dnf
      }

      eventNotifier.notifyListeners(
        event = AppEvent.TimerInspectionUpdate,
        data = arrayOf(countdown, currentPenalty),
        origin = this
      )

      countdown--
    }
  }

  override fun suspendState() {
    repeater!!.cancel()
  }
}
