package com.lucasalfare.fltimer.ui.screens.timer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.configuration.Config
import com.lucasalfare.fltimer.core.model.FLTimerState
import com.lucasalfare.fltimer.ui.raw.FLTimerText
import com.lucasalfare.fltimer.ui.theme.FLTimerTheme
import com.lucasalfare.fltimer.ui.uiManager

@Composable
fun ToggleInspection() {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .clickable {
        uiManager.notifyListeners(
          FLTimerEvent.ConfigSet, arrayOf(
            Config.UseInspection,
            !(FLTimerState.configurations[Config.UseInspection]!! as Boolean)
          )
        )
      }
      .padding(8.dp)
  ) {
    val focusManager = LocalFocusManager.current

    Checkbox(
      checked = FLTimerState.configurations[Config.UseInspection]!! as Boolean,
      onCheckedChange = {
        uiManager.notifyListeners(
          FLTimerEvent.ConfigSet, arrayOf(
            Config.UseInspection,
            !(FLTimerState.configurations[Config.UseInspection]!! as Boolean)
          )
        )

        focusManager.clearFocus()
      }
    )

    FLTimerText(
      text = "Use inspection"
    )
  }
}