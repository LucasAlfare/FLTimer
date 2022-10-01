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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.data.Solves
import lucasalfare.fltimer.ui.ClipboardCharacter
import lucasalfare.fltimer.ui.uiManager

@Composable
fun TimesList(modifier: Modifier = Modifier) {
  var solves by remember { mutableStateOf(Solves()) }

  // list scrolling management
  val lazyListState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()

  var showDetails by remember { mutableStateOf(false) }

  DisposableEffect(true) {
    val callback = uiManager.addCallback { appEvent, data ->
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
        }
        else -> {}
      }
    }

    onDispose { uiManager.removeCallback(callback) }
  }

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .shadow(elevation = 5.dp),
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
}