package com.lucasalfare.fltimer.core.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.lucasalfare.fllistener.EventManageable
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.FLTimerEvent.*
import com.lucasalfare.fltimer.core.configuration.Config
import com.lucasalfare.fltimer.core.timer.fsm.ReadyState
import com.lucasalfare.fltimer.core.timer.fsm.TimerState

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

  override fun onEvent(event: Any, data: Any?, origin: Any?) {
    when (event) {
      NetworkingAllUsersFinished -> {
        networkingCanStart = data as Boolean
      }

      TimerToggleDown, TimerToggleUp -> {
        val nextState: TimerState? = currentState.handleInput(
          event as FLTimerEvent,
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

  override fun onInitiated() {

  }

  override fun onNotInitiated() {
    initiated = true
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
