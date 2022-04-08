package lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.data.Solves
import lucasalfare.fltimer.core.statistics.getAllStatistics
import lucasalfare.fltimer.core.toTimestamp
import lucasalfare.fltimer.ui.uiComponentsManager

@Composable
fun StatisticsList(modifier: Modifier = Modifier) {
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
    solves.getAllStatistics().values.forEach {
      if (it != null) {
        item {
          Button(modifier = Modifier.fillMaxWidth(), onClick = {}) {
            Text("${it.name}: ${it.result.toTimestamp()}")
          }
        }
      }
    }
  }
}