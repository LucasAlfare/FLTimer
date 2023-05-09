package com.lucasalfare.fltimer.ui.screens.stats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lucasalfare.fltimer.core.statistics.StatisticResult

@Composable
fun StatsScreen(onStatisticResultDetailRequest: (StatisticResult) -> Unit = {}) {
  LazyColumn(Modifier.fillMaxSize()) {
    item {
      Box(modifier = Modifier.padding(12.dp)) {
        BasicSelectableAndClickableText(
          text = getStatisticsAnnotatedString(),
          onAnnotationItemClick = {
            val clickedResult = StatisticsTemplateProvider.lookUpVariableValue(it.item)
            println(clickedResult)
            onStatisticResultDetailRequest(clickedResult)
          }
        )
      }
    }
  }
}
