package com.lucasalfare.fltimer.core.timer

import com.lucasalfare.fllistener.EventManageable
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.configuration.Config
import com.lucasalfare.fltimer.core.model.FLTimerModel
import com.lucasalfare.fltimer.core.timer.fsm.ReadyState
import com.lucasalfare.fltimer.core.timer.fsm.TimerState

class TimerManager : EventManageable() {

  private var currentState: TimerState = ReadyState()

  private var useInspection = false
  private var networkingModeOn = false
  private var configsInitiated = false

  /**
   * This field is set to [true] by default once any client
   * is able to start when just connected to the server/room.
   *
   * TODO: refactor this logic to state class
   */
  private var networkingCanStart = true

  override fun onInitiated() {
    println("[TimerManager] Instance initiated.")
  }

  override fun onNotInitiated() {
    useInspection = (FLTimerModel.configurations[Config.UseInspection] as Boolean)
    networkingModeOn = (FLTimerModel.configurations[Config.NetworkingModeOn] as Boolean)
    this.initiated = true
  }

  override fun onEvent(event: Any, data: Any?, origin: Any?) {
    when (event) {
      FLTimerEvent.TimerToggleDown, FLTimerEvent.TimerToggleUp -> {
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

        notifyListeners(event = event, data = data)
      }

      FLTimerEvent.ConfigsUpdate -> {
        val configs = data as MutableMap<*, *>
        useInspection = configs[Config.UseInspection]!! as Boolean
        networkingModeOn = configs[Config.NetworkingModeOn]!! as Boolean
        configsInitiated = true
      }

      FLTimerEvent.NetworkingAllUsersFinished -> {
        networkingCanStart = data as Boolean
      }

      else -> {}
    }
  }
}
