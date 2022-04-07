package lucasalfare.fltimer.core.configuration

import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.EventListener
import lucasalfare.fltimer.core.Listenable

class ConfigurationManager : Listenable(), EventListener {

  private val configurations = mutableMapOf<Config, Any>(
    Pair(Config.UseInspection, false)
  )

  override fun init() {

  }

  override fun onEvent(event: AppEvent, data: Any?) {
    if (event == AppEvent.ConfigSet) {
      val props = data as Array<*>
      val config = props[0] as Config
      val newValue = props[1] as Any
      configurations[config] = newValue

      notifyListeners(AppEvent.ConfigsUpdate, configurations)
    }
  }
}
