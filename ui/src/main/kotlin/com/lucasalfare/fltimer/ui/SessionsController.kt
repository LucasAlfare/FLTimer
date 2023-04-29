package com.lucasalfare.fltimer.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.model.FLTimerState
import com.lucasalfare.fltimer.core.model.session.Session

@Composable
fun SessionController() {
  val sessions = remember { FLTimerState.sessions }
  val currentActiveSessionName = remember { FLTimerState.currentActiveSessionName }

  Row(verticalAlignment = Alignment.CenterVertically) {
    TextButton(
      modifier = Modifier.width(45.dp),
      onClick = {
        uiManager.notifyListeners(
          FLTimerEvent.SessionSwitch,
          getSession(sessions.first { it.name == currentActiveSessionName.value }, sessions, false)
        )
      }
    ) {
      Text("<<")
    }

    Text(currentActiveSessionName.value)

    TextButton(
      modifier = Modifier.width(45.dp),
      onClick = {
        uiManager.notifyListeners(
          FLTimerEvent.SessionSwitch,
          getSession(sessions.first { it.name == currentActiveSessionName.value }, sessions, true)
        )
      }
    ) {
      Text(">>")
    }
  }
}

private fun getSession(
  currentSession: Session,
  sessions: SnapshotStateList<Session>,
  next: Boolean
): Session {
  val currentIndex = sessions.indexOf(currentSession)
  val factor = if (next) 1 else -1
  var targetIndex = (currentIndex + factor) % sessions.size
  if (targetIndex < 0) targetIndex = sessions.size - 1
  return sessions[targetIndex]
}