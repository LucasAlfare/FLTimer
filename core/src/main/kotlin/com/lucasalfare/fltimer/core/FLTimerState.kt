package com.lucasalfare.fltimer.core

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.lucasalfare.fltimer.core.configuration.Config
import com.lucasalfare.fltimer.core.data.DefaultSessionName
import com.lucasalfare.fltimer.core.data.session.Session

data class FLTimerState(
  val configurations: SnapshotStateMap<Config, Any>,
  val sessions: SnapshotStateList<Session>,
  var currentActiveSessionName: String
) {
  companion object {
    private lateinit var flTimerState: FLTimerState

    fun getFLTimerState(): FLTimerState {
      if (!(this::flTimerState.isInitialized)) {
        flTimerState = FLTimerState(
          configurations = mutableStateMapOf(
            Pair(Config.UseInspection, false),
            Pair(Config.ShowScramblesInDetailsUI, false),
            Pair(Config.NetworkingModeOn, false),
            Pair(Config.AskForTimerMode, true)
          ),
          sessions = mutableStateListOf(
            Session(name = DefaultSessionName)
          ),
          currentActiveSessionName = DefaultSessionName
        )
      }

      return flTimerState
    }

    fun getCurrentActiveSession() =
      getFLTimerState()
      .sessions
      .first { it.name == getFLTimerState().currentActiveSessionName }
  }
}