package lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.configuration.Config
import lucasalfare.fltimer.core.data.Solves
import lucasalfare.fltimer.ui.ClipboardCharacter
import lucasalfare.fltimer.ui.uiComponentsManager

@Composable
fun TimesList(modifier: Modifier = Modifier) {
  var solves by remember { mutableStateOf(Solves()) }
  var includeScramblesInDetails by remember { mutableStateOf(false) }

  // list scrolling management
  val lazyListState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()

  var showDetails by remember { mutableStateOf(false) }

  DisposableEffect(true) {
    val callback = uiComponentsManager.addCallback { appEvent, data ->
      when (appEvent) {
        AppEvent.SolvesUpdate -> {
          solves = (data as Solves).clone()

          if (solves.isNotEmpty()) {
            coroutineScope.launch {
              lazyListState.animateScrollToItem(solves.size - 1)
            }
          }
        }

        AppEvent.ConfigsUpdate -> {
          val configurations = data as MutableMap<*, *>
          includeScramblesInDetails = configurations[Config.ShowScramblesInDetailsUI] as Boolean
        }
      }
    }

    onDispose { uiComponentsManager.removeCallback(callback) }
  }

  Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text("Number of solves: ${solves.size}")
    TextButton(onClick = { showDetails = !showDetails }) {
      Text("Copy $ClipboardCharacter")
    }
    LazyColumn(state = lazyListState) {
      solves.values.forEachIndexed { index, solve ->
        item {
          TimesListItem(index = index, solve = solve)
          Divider()
        }
      }
    }
  }

  if (showDetails) {
    SolvesDetails(
      solves = solves,
      showScrambles = includeScramblesInDetails,
      dismissCallback = { showDetails = false }
    )
  }
}