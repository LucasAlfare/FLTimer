package com.lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.data.Session
import com.lucasalfare.fltimer.ui.NextCharacter
import com.lucasalfare.fltimer.ui.PreviousCharacter
import com.lucasalfare.fltimer.ui.uiManager

@Suppress(
  "UNCHECKED_CAST",
  "NON_EXHAUSTIVE_WHEN_STATEMENT"
)
@Composable
fun SessionController() {
  var sessions by remember { mutableStateOf(mutableMapOf<String, Session>()) }
  var currentSessionName by remember { mutableStateOf("") }

  LaunchedEffect(true) {
    uiManager.notifyListeners(FLTimerEvent.SessionsRequestUpdate)
  }

  DisposableEffect(true) {
    val callback = uiManager.addCallback { appEvent, data ->
      when (appEvent) {
        FLTimerEvent.SessionsUpdate -> {
          val args = data as Array<*>
          currentSessionName = args[0] as String
          sessions = args[1] as MutableMap<String, Session>
        }

        else -> {}
      }
    }

    onDispose { uiManager.removeCallback(callback) }
  }

  Row(verticalAlignment = Alignment.CenterVertically) {
    TextButton(onClick = {
      uiManager.notifyListeners(
        FLTimerEvent.SessionSwitch,
        getSession(currentSessionName, sessions, false)
      )
    }) {
      Text(PreviousCharacter)
    }

    Text(currentSessionName)

    TextButton(onClick = {
      uiManager.notifyListeners(
        FLTimerEvent.SessionSwitch,
        getSession(currentSessionName, sessions, true)
      )
    }) {
      Text(NextCharacter)
    }
  }
}

private fun getSession(
  currentSession: String,
  sessions: MutableMap<String, Session>,
  next: Boolean
): String {
  val currentIndex = sessions.keys.indexOf(currentSession)
  val factor = if (next) 1 else -1
  var targetIndex = (currentIndex + factor) % sessions.size
  if (targetIndex < 0) targetIndex = sessions.size - 1
  return sessions.keys.toTypedArray()[targetIndex]
}
