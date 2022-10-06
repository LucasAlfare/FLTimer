package lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.data.Solves
import lucasalfare.fltimer.core.statistics.getStats
import lucasalfare.fltimer.core.toTimestamp
import lucasalfare.fltimer.ui.uiManager

@Composable
fun StatisticsList(modifier: Modifier = Modifier) {
  var solves by remember { mutableStateOf(Solves()) }
  var showDetails by remember { mutableStateOf(false) }
  var relatedSolves by remember { mutableStateOf(Solves()) }

  DisposableEffect(true) {
    val callback = uiManager.addCallback { appEvent, data ->
      when (appEvent) {
        AppEvent.SolvesUpdate -> {
          solves = (data as Solves).clone()
        }
        else -> {}
      }
    }

    onDispose { uiManager.removeCallback(callback) }
  }

  if (solves.isNotEmpty()) {
    LazyColumn(
      modifier = Modifier
        .shadow(4.dp)
        .border(width = 4.dp, shape = RoundedCornerShape(5.dp), color = Color.LightGray)
        .padding(8.dp)
        .fillMaxSize()
    ) {
      solves.getStats().forEach {
        item {
          TextButton(modifier = Modifier.fillMaxWidth(), onClick = {
            showDetails = !showDetails
            relatedSolves = it.related
          }) {
            Text(
              text = buildAnnotatedString {
                append("${it.name}:\n")
                withStyle(
                  SpanStyle(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                  )
                ) {
                  append(it.result.toTimestamp())
                }
              },
              textAlign = TextAlign.Center
            )
          }
          Divider()
        }
      }
    }
  }
}