package com.lucasalfare.fltimer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lucasalfare.fltimer.ui.FLTimerUiState.Companion.canListenToggling
import com.lucasalfare.fltimer.ui.FLTimerUiState.Companion.currentTabName

@Composable
fun Tabs() {
  Row(modifier = Modifier.fillMaxWidth().height(80.dp)) {
    TabItem(Modifier.weight(1f), "Timer", true)
    TabItem(Modifier.weight(1f), "Solves", false)
    TabItem(Modifier.weight(1f), "Stats", false)
    TabItem(Modifier.weight(1f), "Configs", false)
  }
}

@Composable
fun TabItem(modifier: Modifier, tabName: String, toggleValue: Boolean) {
  Button(
    modifier = modifier
      .fillMaxHeight()
      .padding(4.dp),
    onClick = {
      currentTabName.value = tabName
      canListenToggling.value = toggleValue
    },
    enabled = currentTabName.value != tabName
  ) {
    Text(tabName)
  }
}