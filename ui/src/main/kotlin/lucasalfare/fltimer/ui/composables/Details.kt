package lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.launch
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.configuration.Config
import lucasalfare.fltimer.core.data.Solves
import lucasalfare.fltimer.ui.getSessionResume
import lucasalfare.fltimer.ui.uiComponentsManager

@Composable
fun SolvesDetails(solves: Solves, showScrambles: Boolean) {
  var showMenu by remember { mutableStateOf(true) }
  var checked by remember { mutableStateOf(showScrambles) }
  var content by remember {
    mutableStateOf(
      TextFieldValue(
        getSessionResume(
          solves = solves,
          includeScrambles = checked
        )
      )
    )
  }

  DisposableEffect(true) {
    val callback = uiComponentsManager.addCallback { appEvent, _ ->
      when (appEvent) {
        AppEvent.ConfigsUpdate -> {
          content = TextFieldValue(
            getSessionResume(
              solves = solves,
              includeScrambles = checked
            )
          )
        }
      }
    }

    onDispose { uiComponentsManager.removeCallback(callback) }
  }

  DropdownMenu(
    expanded = showMenu,
    onDismissRequest = { showMenu = false }
  ) {
    val scope = rememberCoroutineScope()

    // start coroutine
    scope.launch {
      val text = content.text
      content = content.copy(
        selection = TextRange(0, text.length)
      )
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
      Checkbox(checked = checked, onCheckedChange = {
        checked = !checked
        uiComponentsManager.notifyListeners(AppEvent.ConfigSet, arrayOf(Config.ShowScramblesInDetailsUI, checked))
      })
      Text("show scrambles")
    }

    TextField(
      value = content,
      onValueChange = { /*pass*/ }
    )
  }
}