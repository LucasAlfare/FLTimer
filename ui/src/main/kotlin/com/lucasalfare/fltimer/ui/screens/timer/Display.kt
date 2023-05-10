package com.lucasalfare.fltimer.ui.screens.timer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lucasalfare.fltimer.core.model.FLTimerState
import com.lucasalfare.fltimer.ui.BasicSelectableText
import com.lucasalfare.fltimer.ui.FLTimerUiState.Companion.timerPressingDown
import com.lucasalfare.fltimer.ui.theme.FLTimerTheme

@Composable
fun Display() {
  val displayText = remember { FLTimerState.currentDisplayValue }

  Box(
    Modifier
      .padding(12.dp)
  ) {
    BasicSelectableText(
      text = displayText.value,
      textStyle = if (timerPressingDown.value) {
        FLTimerTheme.typography.semiBig
      } else {
        FLTimerTheme.typography.extraBig
      }
    )
  }
}