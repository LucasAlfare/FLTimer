package com.lucasalfare.fltimer.a_domain

open class Listenable {

  private val listeners = mutableListOf<Listener>()

  fun addListener(listener: Listener) {
    if (!listeners.contains(listener)) {
      listeners += listener
    }
  }

  suspend fun notifyListeners(event: String, data: Any? = null) {
    listeners.forEach {
      it.onEvent(event, data)
    }
  }
}