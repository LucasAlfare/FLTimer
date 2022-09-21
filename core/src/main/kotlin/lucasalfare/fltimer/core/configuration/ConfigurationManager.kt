package lucasalfare.fltimer.core.configuration

import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.EventManageable

class ConfigurationManager : EventManageable() {

  private val configurations = mutableMapOf<Config, Any>(
    Pair(Config.UseInspection, false),
    Pair(Config.ShowScramblesInDetailsUI, true)
  )

  override fun init() {
    notifyListeners(event = AppEvent.ConfigsUpdate, data = configurations, origin = this)
  }

  override fun onEvent(event: AppEvent, data: Any?, origin: Any?) {
    if (event == AppEvent.ConfigSet) {
      println("getting a set request...")
      val props = data as Array<*>
      val config = props[0] as Config
      val newValue = props[1] as Any
      configurations[config] = newValue

      println(configurations)

      notifyListeners(event = AppEvent.ConfigsUpdate, data = configurations, origin = this)
    }
  }
}
