package lucasalfare.fltimer.core.timer.fsm

import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.Listenable
import lucasalfare.fltimer.core.getCurrentTime
import lucasalfare.fltimer.core.timer.asyncRoutine
import kotlinx.coroutines.Job
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
      suspend()
      return FinishState(start)
    }
    return null
  }

  override fun update(eventNotifier: Listenable, data: Any?) {
    logger.d("current SOLVING...")
    eventNotifier.notifyListeners(AppEvent.TimerStarted)
    start = data as Long
    repeater = asyncRoutine {
      elapsed = getCurrentTime() - start
      eventNotifier.notifyListeners(AppEvent.TimerUpdate, elapsed)
    }
  }

  override fun suspend() {
    repeater!!.cancel()
  }
}