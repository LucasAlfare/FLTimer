package com.lucasalfare.fltimer.core.configuration

import com.lucasalfare.fllistener.EventManageable
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.model.FLTimerState

class ConfigurationsManager : EventManageable() {

  override fun onInitiated() {
    println("[ConfigurationsManager] Instance inititated.")
  }

  override fun onNotInitiated() {
    this.initiated = true
  }

  override fun onEvent(event: Any, data: Any?, origin: Any?) {
    if (event == FLTimerEvent.ConfigSet) {
      val props = data as Array<*>
      val targetConfig = props[0] as Config
      val nextValue = props[1] as Any
      FLTimerState.configurations[targetConfig] = nextValue

      notifyListeners(FLTimerEvent.ConfigsUpdate)
    }
  }
}