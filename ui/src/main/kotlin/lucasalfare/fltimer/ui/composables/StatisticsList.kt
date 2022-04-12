package lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.data.Solves
import lucasalfare.fltimer.core.statistics.getAllStatistics
import lucasalfare.fltimer.core.statistics.getStats
import lucasalfare.fltimer.core.toTimestamp
import lucasalfare.fltimer.ui.uiComponentsManager

@Composable
fun StatisticsList(modifier: Modifier = Modifier) {
  var solves by remember { mutableStateOf(Solves()) }
  var showDetails by remember { mutableStateOf(false) }
  var targetDetailsSolves by remember { mutableStateOf(Solves()) }

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
    solves.getStats().forEach {
      item {
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
          showDetails = !showDetails
          targetDetailsSolves = it.related
        }) {
          Text("${it.name}: ${it.result.toTimestamp()}")
        }
        Divider()
      }
    }
  }

  if (showDetails) {
    SolvesDetails(targetDetailsSolves)
  }
}