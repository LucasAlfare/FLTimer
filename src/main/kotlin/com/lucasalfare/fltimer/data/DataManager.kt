package com.lucasalfare.fltimer.data

import com.lucasalfare.fllistening.EventManageable
import com.lucasalfare.fltimer.Event

class DataManager : EventManageable() {
  override suspend fun initialize() {
    initialized = true
  }

  override fun onEvent(event: Any, data: Any?) {
    if (event == Event.TimerFinish) {
      val solveTime = data as Long
      Solves.create(time = solveTime)
      println(Solves.getAll())
    }
  }
}
