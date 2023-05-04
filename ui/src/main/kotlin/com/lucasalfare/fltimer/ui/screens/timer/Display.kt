package com.lucasalfare.fltimer.ui.screens.timer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lucasalfare.fltimer.core.model.FLTimerState
import com.lucasalfare.fltimer.ui.BasicSelectableText
import com.lucasalfare.fltimer.ui.FLTimerUiState.Companion.timerPressingDown

@Composable
fun Display() {
  val displayText = remember { FLTimerState.currentDisplayValue }

  Box(
    Modifier
      .padding(12.dp)
  ) {
    BasicSelectableText(
      text = displayText.value,
      textStyle = TextStyle(
        fontSize = if (timerPressingDown.value) 45.sp else 65.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold
      )
    )
  }
}