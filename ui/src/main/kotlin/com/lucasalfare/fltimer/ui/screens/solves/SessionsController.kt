package com.lucasalfare.fltimer.ui.screens.solves

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.model.FLTimerState
import com.lucasalfare.fltimer.core.model.session.Session
import com.lucasalfare.fltimer.ui.FLTimerUiState
import com.lucasalfare.fltimer.ui.uiManager

@Composable
fun SessionsController() {
  val sessions = remember { FLTimerState.sessions }
  val currentActiveSessionName = remember { FLTimerState.currentActiveSessionName }

  Column(modifier = Modifier.padding(12.dp)) {
    Row(
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
      ) { Text("<<") }

      Spacer(Modifier.width(4.dp))

      Text(
        text = currentActiveSessionName.value,
        textAlign = TextAlign.Center,
      )

      Spacer(Modifier.width(4.dp))

      TextButton(
        modifier = Modifier.width(45.dp),
        onClick = {
          uiManager.notifyListeners(
            FLTimerEvent.SessionSwitch,
            getSession(sessions.first { it.name == currentActiveSessionName.value }, sessions, true)
          )
        }
      ) { Text(">>") }

      Spacer(Modifier.width(4.dp))

      Button(
        modifier = Modifier.weight(1f),
        onClick = {
//          uiManager.notifyListeners(FLTimerEvent.SessionRemove, currentActiveSessionName.value)
//          uiManager.notifyListeners(FLTimerEvent.SessionSwitch, FLTimerState.DEFAULT_SESSION_NAME)
        },
        contentPadding = PaddingValues(4.dp)
      ) {
        Text("-")
      }

      Spacer(Modifier.width(4.dp))

      Button(
        modifier = Modifier.weight(1f),
        onClick = {
          FLTimerUiState.creatingSessionMode.value = !FLTimerUiState.creatingSessionMode.value
        },
        enabled = !FLTimerUiState.creatingSessionMode.value,
        contentPadding = PaddingValues(4.dp)
      ) {
        Text("+")
      }
    }

    if (FLTimerUiState.creatingSessionMode.value) {
      SessionInsertionForm()
    }
  }
}

@Composable
fun SessionInsertionForm() {
  val tmpNewSessionName = remember { mutableStateOf("") }

  Column {
    Row(
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text("New session name:")
      TextField(
        modifier = Modifier.fillMaxWidth(),
        value = tmpNewSessionName.value,
        onValueChange = {
          tmpNewSessionName.value = it
        }
      )
    }

    Button(
      modifier = Modifier.fillMaxWidth(),
      onClick = {
        if (tmpNewSessionName.value.isNotEmpty()) {
          uiManager.notifyListeners(FLTimerEvent.SessionCreate, tmpNewSessionName.value)
          uiManager.notifyListeners(FLTimerEvent.SessionSwitch, tmpNewSessionName.value)
          tmpNewSessionName.value = ""
        }

        FLTimerUiState.creatingSessionMode.value = false
      }
    ) {
      Text(text = if (tmpNewSessionName.value.isEmpty()) "Cancel" else "Create")
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