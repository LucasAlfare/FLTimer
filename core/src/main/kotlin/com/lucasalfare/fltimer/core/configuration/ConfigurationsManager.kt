package com.lucasalfare.fltimer.core.configuration

import com.lucasalfare.fllistener.EventManageable
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.FLTimerState

class ConfigurationsManager : EventManageable() {

  private val configsRef = FLTimerState.getFLTimerState().configurations

  override fun onInitiated() {
//    println("[ConfigurationsManager] Instance inititated.")
  }

  override fun onNotInitiated() {
    notifyListeners(FLTimerEvent.ConfigsUpdate, configsRef)
    this.initiated = true
  }

  override fun onEvent(event: Any, data: Any?, origin: Any?) {
    if (event == FLTimerEvent.ConfigSet) {
      val props = data as Array<*>
      val targetConfig = props[0]!! as Config
      val targetValue = props[1]!!
      configsRef[targetConfig] = targetValue

      notifyListeners(FLTimerEvent.ConfigsUpdate, configsRef)
    }
  }
}