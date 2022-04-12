package lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.launch
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.data.Solves
import lucasalfare.fltimer.ui.ClipboardCharacter
import lucasalfare.fltimer.ui.getClipboardResume
import lucasalfare.fltimer.ui.uiComponentsManager

@Composable
fun TimesList(modifier: Modifier = Modifier) {
  var solves by remember { mutableStateOf(Solves()) }

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
      }
    }

    onDispose { uiComponentsManager.removeCallback(callback) }
  }

  Text("Number of solves: ${solves.size}")
  TextButton(onClick = {
    showDetails = !showDetails
  }) {
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

  if (showDetails) {
    SolvesDetails(solves)
  }
}