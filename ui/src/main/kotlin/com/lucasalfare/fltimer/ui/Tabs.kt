package com.lucasalfare.fltimer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lucasalfare.fltimer.ui.FLTimerUiState.Companion.canTimerToggle
import com.lucasalfare.fltimer.ui.FLTimerUiState.Companion.currentTabName
import com.lucasalfare.fltimer.ui.raw.FLTimerButton

@Composable
fun Tabs() {
  Row(modifier = Modifier.fillMaxWidth().height(80.dp)) {
    TabButton(Modifier.weight(1f), TabName.Timer.name, true)
    TabButton(Modifier.weight(1f), TabName.Details.name, false)
    TabButton(Modifier.weight(1f), TabName.Configs.name, false)
  }
}

@Composable
fun TabButton(modifier: Modifier, tabName: String, initialTimerTogglingPermission: Boolean) {
  FLTimerButton(
    text = tabName,
    enabled = currentTabName.value != tabName,
    modifier = modifier
      .fillMaxHeight()
      .padding(4.dp),
    onClick = {
      currentTabName.value = tabName
      canTimerToggle.value = initialTimerTogglingPermission
    }
  )
}