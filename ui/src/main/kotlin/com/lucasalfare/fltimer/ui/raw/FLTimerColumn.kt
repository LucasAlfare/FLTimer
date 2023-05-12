package com.lucasalfare.fltimer.ui.raw

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.lucasalfare.fltimer.ui.theme.FLTimerTheme

@Composable
fun FLTimerColumn(
  modifier: Modifier = Modifier,
  background: Color = FLTimerTheme.colors.surface,
  verticalArrangement: Arrangement.Vertical = Arrangement.Top,
  horizontalAlignment: Alignment.Horizontal = Alignment.Start,
  content: @Composable ColumnScope.() -> Unit
) {
  Column(
    modifier = modifier.background(background),
    verticalArrangement = verticalArrangement,
    horizontalAlignment = horizontalAlignment
  ) {
    content()
  }
}