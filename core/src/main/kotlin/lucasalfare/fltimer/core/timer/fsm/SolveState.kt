package lucasalfare.fltimer.core.timer.fsm

import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.getCurrentTime
import lucasalfare.fltimer.core.timer.asyncRoutine
import kotlinx.coroutines.Job
import lucasalfare.fltimer.core.EventManageable
import lucasalfare.fltimer.core.L

class SolveState : TimerState {

  private var repeater: Job? = null
  private var start = 0L
  private var elapsed = 0L

  val logger = L()

  init {
    logger.logAllowed = false
  }

  override fun handleInput(inputType: AppEvent, data: Any?): TimerState? {
    if (inputType == InputPress) {
      suspendState()
      return FinishState(start)
    }
    return null
  }

  override fun update(eventNotifier: EventManageable, data: Any?) {
    logger.d("current SOLVING...")
    eventNotifier.notifyListeners(event = AppEvent.TimerStarted, origin = this)
    start = data as Long
    repeater = asyncRoutine {
      elapsed = getCurrentTime() - start
      eventNotifier.notifyListeners(event = AppEvent.TimerUpdate, data = elapsed, origin = this)
    }
  }

  override fun suspendState() {
    repeater!!.cancel()
  }
}