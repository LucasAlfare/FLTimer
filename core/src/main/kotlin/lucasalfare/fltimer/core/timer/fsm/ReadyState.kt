package lucasalfare.fltimer.core.timer.fsm

import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.L
import lucasalfare.fltimer.core.Listenable

class ReadyState : TimerState {

  val logger = L()

  init {
    logger.logAllowed = false
  }

  override fun handleInput(inputType: AppEvent, data: Any?): TimerState? {
    if (inputType == InputRelease) {
      if (data != null) {
        val useInspection = data as Boolean
        return if (useInspection) InspectState() else SolveState()
      }
    }
    return null
  }

  override fun update(eventNotifier: Listenable, data: Any?) {
    logger.d("current READY (again?)")
    eventNotifier.notifyListeners(AppEvent.TimerReady)
  }

  override fun suspend() {

  }
}