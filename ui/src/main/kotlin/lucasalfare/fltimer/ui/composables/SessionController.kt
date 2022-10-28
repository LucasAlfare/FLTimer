package lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.data.Session
import lucasalfare.fltimer.ui.NextCharacter
import lucasalfare.fltimer.ui.PreviousCharacter
import lucasalfare.fltimer.ui.uiManager

@Suppress(
  "UNCHECKED_CAST",
  "NON_EXHAUSTIVE_WHEN_STATEMENT"
)
@Composable
fun SessionController() {
  var sessions by remember { mutableStateOf(mutableMapOf<String, Session>()) }
  var currentSessionName by remember { mutableStateOf("") }

  LaunchedEffect(true) {
    uiManager.notifyListeners(AppEvent.SessionsRequestUpdate)
  }

  DisposableEffect(true) {
    val callback = uiManager.addCallback { appEvent, data ->
      when (appEvent) {
        AppEvent.SessionsUpdate -> {
          val args = data as Array<*>
          currentSessionName = args[0] as String
          sessions = args[1] as MutableMap<String, Session>
        }
      }
    }

    onDispose { uiManager.removeCallback(callback) }
  }

  Row(verticalAlignment = Alignment.CenterVertically) {
    TextButton(onClick = {
      uiManager.notifyListeners(
        AppEvent.SessionSwitch,
        getSession(currentSessionName, sessions, false)
      )
    }) {
      Text(PreviousCharacter)
    }

    Text(currentSessionName)

    TextButton(onClick = {
      uiManager.notifyListeners(
        AppEvent.SessionSwitch,
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
