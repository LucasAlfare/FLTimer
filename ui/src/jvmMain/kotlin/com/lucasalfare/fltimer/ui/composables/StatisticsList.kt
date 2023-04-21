package com.lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.lucasalfare.fltimer.core.AppEvent
import com.lucasalfare.fltimer.core.data.Solves
import com.lucasalfare.fltimer.core.statistics.getStats
import com.lucasalfare.fltimer.core.toTimestamp
import com.lucasalfare.fltimer.ui.uiManager

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
    Column {
      Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
          uiManager.notifyListeners(event = AppEvent.StatisticRequest, data = "all", origin = this)
        }
      ) {
        Text("See all details...")
      }

      LazyColumn(
        modifier = Modifier
          .shadow(4.dp)
          .padding(8.dp)
          .fillMaxSize()
      ) {
        solves.getStats().forEach {
          if (it.name != "Not Calculated" && it.name != "DNF statistic") {
            item {
              TextButton(modifier = Modifier.fillMaxWidth(), onClick = {
                showDetails = !showDetails
                relatedSolves = it.related

                uiManager.notifyListeners(
                  event = AppEvent.StatisticRequest,
                  data = it.name,
                  origin = this
                )
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
  }
}