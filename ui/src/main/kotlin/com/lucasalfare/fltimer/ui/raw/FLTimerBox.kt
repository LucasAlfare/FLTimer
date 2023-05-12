package com.lucasalfare.fltimer.ui.raw

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.lucasalfare.fltimer.ui.theme.FLTimerTheme

@Composable
fun FLTimerBox(
  modifier: Modifier = Modifier,
  background: Color = FLTimerTheme.colors.surface,
  content: @Composable BoxScope.() -> Unit
) {
  Box(
    modifier = modifier.background(background)
  ) {
    content()
  }
}