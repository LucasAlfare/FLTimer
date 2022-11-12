package lucasalfare.fltimer.core.configuration

import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.EventManageable

class ConfigurationManager : EventManageable() {

  private var configurations = mutableMapOf<Config, Any>(
    Pair(Config.UseInspection, false),
    Pair(Config.ShowScramblesInDetailsUI, false),
    Pair(Config.NetworkingModeOn, false),
    Pair(Config.AskForTimerMode, true)
  )

  override fun init() {
    notifyListeners(
      event = AppEvent.ConfigsUpdate,
      data = configurations,
      origin = this
    )
  }

  override fun onEvent(event: AppEvent, data: Any?, origin: Any?) {
    if (event == AppEvent.ConfigSet) {
      val props = data as Array<*>
      val config = props[0] as Config
      val newValue = props[1] as Any
      configurations[config] = newValue

      notifyListeners(event = AppEvent.ConfigsUpdate, data = configurations, origin = this)
    } else if (event == AppEvent.PersistenceUpdate) {
      val props = data as Array<*>
      configurations = props[0] as MutableMap<Config, Any>
      notifyListeners(event = AppEvent.ConfigsUpdate, data = configurations, origin = this)
    }
  }
}
