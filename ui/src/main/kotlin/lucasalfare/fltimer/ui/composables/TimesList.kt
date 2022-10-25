package lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.data.Solves
import lucasalfare.fltimer.ui.ClipboardCharacter
import lucasalfare.fltimer.ui.uiManager

@Composable
fun TimesList() {
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
        else -> {}
      }
    }

    onDispose { uiManager.removeCallback(callback) }
  }

  Column(
    modifier = Modifier
      .shadow(4.dp)
      .padding(8.dp)
      .fillMaxWidth()
      .fillMaxHeight(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(text = "Number of solves: ${solves.size}", fontSize = 12.sp)
    TextButton(onClick = { showDetails = !showDetails }) {
      Text("Copy $ClipboardCharacter")
    }
    LazyColumn(
      modifier = Modifier
        .border(
          width = 1.dp,
          shape = RoundedCornerShape(5.dp),
          color = Color.LightGray
        ), state = lazyListState
    ) {
      solves.values.forEachIndexed { index, solve ->
        item {
          TimesListItem(index = index, solve = solve)
          Divider(startIndent = 5.dp, modifier = Modifier.padding(end = 5.dp))
        }
      }
    }
  }
}
