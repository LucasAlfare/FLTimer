package lucasalfare.fltimer.core.data.persistence

import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.EventManageable

class PersistenceManager : EventManageable() {

  override fun init() {
    // TODO: parse file content here;
    // TODO: notify listeners with retrieved data;
  }

  override fun onEvent(event: AppEvent, data: Any?, origin: Any?) {

  }
}
