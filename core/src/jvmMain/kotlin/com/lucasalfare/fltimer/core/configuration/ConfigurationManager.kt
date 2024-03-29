package com.lucasalfare.fltimer.core.configuration

import com.lucasalfare.fllistener.EventManageable
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.FLTimerStateModel


class ConfigurationManager : EventManageable() {

  private var configurations = FLTimerStateModel
    .getFLTimerStateModel()
    .configurations

  override fun onInitiated() {

  }

  override fun onNotInitiated() {
    notifyListeners(
      event = FLTimerEvent.ConfigsUpdate,
      data = configurations,
      origin = this
    )
    initiated = true
  }

  override fun onEvent(event: Any, data: Any?, origin: Any?) {
    if (event == FLTimerEvent.ConfigSet) {
      val props = data as Array<*>
      val config = props[0] as Config
      val newValue = props[1] as Any
      configurations[config] = newValue

      notifyListeners(event = FLTimerEvent.ConfigsUpdate, data = configurations, origin = this)
    } else if (event == FLTimerEvent.PersistenceUpdate) {
      val props = data as Array<*>
      configurations = props[0] as MutableMap<Config, Any>
      notifyListeners(event = FLTimerEvent.ConfigsUpdate, data = configurations, origin = this)
    }
  }
}
