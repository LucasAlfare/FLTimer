package com.lucasalfare.fltimer.data

import com.lucasalfare.fllistening.EventManageable
import com.lucasalfare.fltimer.TimerEvent

class DataManager : EventManageable() {
  override suspend fun initialize() {
    initialized = true
  }

  /**
   * Here we must handle events of:
   *
   * - Inspection finish;
   * - Timer finish;
   * - SolvesDataModify;
   * - Scramble generated;
   * - Preference update;
   */
  override fun onEvent(event: Any, data: Any?) {
    if (event == TimerEvent.TimerFinish) {
      val solveTime = data as Long
    }
  }
}
