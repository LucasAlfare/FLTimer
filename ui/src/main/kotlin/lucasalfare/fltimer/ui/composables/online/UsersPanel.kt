package lucasalfare.fltimer.ui.composables.online

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.ui.uiManager

@Composable
fun UsersPanel() {
  DisposableEffect(true) {
    val callback = uiManager.addCallback { appEvent, any ->
      when (appEvent) {
        AppEvent.NetworkingUsersUpdate -> {

        }
        else -> { }
      }
    }

    onDispose { uiManager.removeCallback(callback) }
  }
}