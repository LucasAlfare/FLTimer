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
import lucasalfare.fltimer.core.timer.fsm.FinishState
import lucasalfare.fltimer.core.timer.fsm.SolveState

class TimerManager : EventManageable() {

  private var currentState: TimerState = ReadyState()
  private var useInspection: Boolean = false
  private var networkingModeOn: Boolean = false

  /**
   * This field is set to [true] by default once any client
   * is able to start when just connected to the server/room.
   *
   * TODO: refactor this logic to state class
   */
  private var networkingCanStart = true

  override fun init() {}

  override fun onEvent(event: AppEvent, data: Any?, origin: Any?) {
    when (event) {
      NetworkingAllUsersFinished -> {
        networkingCanStart = data as Boolean
      }

      TimerToggleDown, TimerToggleUp -> {
        val nextState: TimerState? = currentState.handleInput(
          event,
          booleanArrayOf(
            useInspection,
            networkingModeOn,
            networkingCanStart
          )
        )
        if (nextState != null) {
          currentState = nextState
          currentState.update(
            eventManageable = this,
            data = arrayOf(
              data as Long,
              fun() { networkingCanStart = false }
            )
          )
        }

        notifyListeners(event = event, data = data, origin = this)
      }

      TimerCancel -> {
        currentState.suspendState()
        currentState = ReadyState()
      }

      ConfigsUpdate -> {
        val configurations = data as MutableMap<*, *>
        useInspection = configurations[Config.UseInspection] as Boolean
        networkingModeOn = configurations[Config.NetworkingModeOn] as Boolean
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
