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
    TabItem(Modifier.weight(1f), TabName.Timer.name, true)
    TabItem(Modifier.weight(1f), TabName.Solves.name, false)
    TabItem(Modifier.weight(1f), TabName.Stats.name, false)
    TabItem(Modifier.weight(1f), TabName.Configs.name, false)
  }
}

@Composable
fun TabItem(modifier: Modifier, tabName: String, initialTogglingPermission: Boolean) {
  Button(
    modifier = modifier
      .fillMaxHeight()
      .padding(4.dp),
    onClick = {
      currentTabName.value = tabName
      canListenToggling.value = initialTogglingPermission
    },
    enabled = currentTabName.value != tabName
  ) {
    Text(tabName)
  }
}