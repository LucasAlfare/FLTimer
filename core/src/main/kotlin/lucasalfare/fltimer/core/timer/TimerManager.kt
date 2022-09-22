package lucasalfare.fltimer.core.timer

import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.AppEvent.*
import lucasalfare.fltimer.core.configuration.Config
import lucasalfare.fltimer.core.timer.fsm.ReadyState
import lucasalfare.fltimer.core.timer.fsm.TimerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lucasalfare.fltimer.core.EventManageable

class TimerManager : EventManageable() {

  private var currentState: TimerState = ReadyState()
  private var useInspection: Boolean = false

  override fun init() {}

  override fun onEvent(event: AppEvent, data: Any?, origin: Any?) {
    when (event) {
      TimerToggleDown, TimerToggleUp -> {
        val nextState: TimerState? = currentState.handleInput(event, useInspection)
        if (nextState != null) {
          currentState = nextState
          currentState.update(eventManageable = this, data = data as Long)
        }

        // re-sends the event, normally target is UI
        //TODO: create dedicated event to this to avoid other
        //  interested in [TimerToggleDown, TimerToggleUp, TimerCancel]
        //  receive null data
        //notifyListeners(event = event, origin = this)
      }

      TimerCancel -> {
        if (currentState != null) {
          currentState.suspendState()
          currentState = ReadyState()
          // re-sends the event, normally target is UI
          //TODO: create dedicated event to this to avoid other
          //  interested in [TimerToggleDown, TimerToggleUp, TimerCancel]
          //  receive null data
          //notifyListeners(event = event, origin = this)
        }
      }

      ConfigsUpdate -> {
        val configurations = data as MutableMap<*, *>
        useInspection = configurations[Config.UseInspection] as Boolean
      }
      else -> {}
    }
  }
}

private val AuxCoroutineScope = CoroutineScope(Job())
fun asyncRoutine(
  delayTime: Long = 1L,
  callback: () -> Unit
) = AuxCoroutineScope.launch {
  while (true) {
    callback()
    delay(delayTime)
  }
}
