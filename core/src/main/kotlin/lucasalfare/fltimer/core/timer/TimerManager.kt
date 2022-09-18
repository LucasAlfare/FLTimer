package lucasalfare.fltimer.core.timer

import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.AppEvent.*
import lucasalfare.fltimer.core.EventListener
import lucasalfare.fltimer.core.Listenable
import lucasalfare.fltimer.core.configuration.Config
import lucasalfare.fltimer.core.timer.fsm.ReadyState
import lucasalfare.fltimer.core.timer.fsm.TimerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerManager : Listenable(), EventListener {

  private var currentState: TimerState = ReadyState()
  private var useInspection: Boolean = false

  override fun init() {

  }

  override fun onEvent(event: AppEvent, data: Any?) {
    when (event) {
      TimerToggleDown, TimerToggleUp -> {
        val nextState: TimerState? = currentState.handleInput(event, useInspection)
        if (nextState != null) {
          currentState = nextState
          currentState.update(eventNotifier = this, data = data as Long)
        }

        // re-sends the toggle event, normally target is UI
        notifyListeners(event)
      }

      TimerCancel -> {
        if (currentState != null) {
          currentState.suspend()
          currentState = ReadyState()
          // re-sends the event, normally target is UI
          notifyListeners(event)
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
