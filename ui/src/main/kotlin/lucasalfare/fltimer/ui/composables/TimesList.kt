package lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.data.Solves
import lucasalfare.fltimer.ui.uiComponentsManager

@Composable
fun TimesList(modifier: Modifier = Modifier) {
  var solves by remember { mutableStateOf(Solves()) }

  DisposableEffect(true) {
    val callback = uiComponentsManager.addCallback { appEvent, data ->
      when (appEvent) {
        AppEvent.SolvesUpdate -> {
          solves = (data as Solves).clone()
        }
      }
    }

    onDispose { uiComponentsManager.removeCallback(callback) }
  }

  LazyColumn {
    solves.values.forEachIndexed { index, solve ->
      item {
        TimesListItem(index = index, solve = solve)
        Divider()
      }
    }
  }
}