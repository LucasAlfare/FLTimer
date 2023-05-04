package com.lucasalfare.fltimer.ui.screens.solves

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.model.FLTimerState
import com.lucasalfare.fltimer.core.model.session.Session
import com.lucasalfare.fltimer.ui.uiManager

@Composable
fun SessionsController() {
  val sessions = remember { FLTimerState.sessions }
  val currentActiveSessionName = remember { FLTimerState.currentActiveSessionName }

  Row(
    modifier = Modifier
      .padding(12.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
  ) {
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

    Text(
      text = currentActiveSessionName.value,
      textAlign = TextAlign.Center,
      modifier = Modifier.width(150.dp)
    )

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