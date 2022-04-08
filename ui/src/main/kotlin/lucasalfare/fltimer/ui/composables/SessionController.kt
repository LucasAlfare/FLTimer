package lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.data.Session
import lucasalfare.fltimer.ui.NextCharacter
import lucasalfare.fltimer.ui.PreviousCharacter
import lucasalfare.fltimer.ui.uiComponentsManager

@Composable
fun SessionController(modifier: Modifier = Modifier) {
  var sessions by remember { mutableStateOf(mutableMapOf<String, Session>()) }
  var currentSessionName by remember { mutableStateOf("") }

  DisposableEffect(true) {
    val callback = uiComponentsManager.addCallback { appEvent, data ->
      when (appEvent) {
        AppEvent.SessionsUpdate -> {
          val args = data as Array<*>
          currentSessionName = args[0] as String
          sessions = args[1] as MutableMap<String, Session>
        }
      }
    }

    onDispose { uiComponentsManager.removeCallback(callback) }
  }

  Row {
    TextButton(onClick = {
      uiComponentsManager.notifyListeners(
        AppEvent.SessionSwitch,
        getSession(currentSessionName, sessions, false)
      )
    }) { Text(PreviousCharacter) }

    Text("current: $currentSessionName")

    TextButton(onClick = {
      uiComponentsManager.notifyListeners(
        AppEvent.SessionSwitch,
        getSession(currentSessionName, sessions, true)
      )
    }) { Text(NextCharacter) }
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
