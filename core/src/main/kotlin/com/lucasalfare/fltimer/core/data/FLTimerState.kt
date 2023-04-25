package com.lucasalfare.fltimer.core.data

import com.lucasalfare.fltimer.core.configuration.Config

data class FLTimerState(
  val configurations: MutableMap<Config, Any>,
  val sessions: MutableList<Session>,
  var currentActiveSessionName: String
) {
  companion object {
    private lateinit var flTimerState: FLTimerState

    fun getFLTimerState(): FLTimerState {
      if (!(this::flTimerState.isInitialized)) {
        flTimerState = FLTimerState(
          configurations = mutableMapOf(
            Pair(Config.UseInspection, false),
            Pair(Config.ShowScramblesInDetailsUI, false),
            Pair(Config.NetworkingModeOn, false),
            Pair(Config.AskForTimerMode, true)
          ),
          sessions = mutableListOf(
            Session(name = DefaultSessionName)
          ),
          currentActiveSessionName = DefaultSessionName
        )
      }

      return flTimerState
    }
  }
}