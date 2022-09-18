package lucasalfare.fltimer.core.timer.fsm

import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.EventManageable
import lucasalfare.fltimer.core.L

class FinishState(private val start: Long) : TimerState {

  val logger = L()

  init {
    logger.logAllowed = false
  }

  override fun handleInput(inputType: AppEvent, data: Any?): TimerState? {
    if (inputType == InputRelease) {
      return ReadyState()
    }
    return null
  }

  override fun update(eventNotifier: EventManageable, data: Any?) {
    logger.d("current FINISHING the round...")
    //diff between values sent by UI is authoritative
    val realElapsed = (data as Long) - start
    eventNotifier.notifyListeners(AppEvent.TimerUpdate, realElapsed)
    eventNotifier.notifyListeners(AppEvent.TimerFinished, realElapsed)
  }

  override fun suspend() {

  }
}