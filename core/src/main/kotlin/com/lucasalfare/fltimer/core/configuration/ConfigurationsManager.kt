package com.lucasalfare.fltimer.core.configuration

import com.lucasalfare.fllistener.EventManageable
import com.lucasalfare.fltimer.core.FLTimerEvent

class ConfigurationsManager : EventManageable() {

  override fun onInitiated() {
    println("[ConfigurationsManager] Instance inititated.")
  }

  override fun onNotInitiated() {

    this.initiated = true
  }

  override fun onEvent(event: Any, data: Any?, origin: Any?) {
    if (event == FLTimerEvent.ConfigSet) {

    }
  }
}