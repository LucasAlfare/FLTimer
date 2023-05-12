package com.lucasalfare.fltimer.ui.raw

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.lucasalfare.fltimer.ui.theme.FLTimerTheme

@Composable
fun FLTimerRow(
  modifier: Modifier = Modifier,
  background: Color = FLTimerTheme.colors.surface,
  horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
  verticalAlignment: Alignment.Vertical = Alignment.Top,
  content: @Composable RowScope.() -> Unit
) {
  Row(
    modifier = modifier.background(background),
    horizontalArrangement = horizontalArrangement,
    verticalAlignment = verticalAlignment
  ) {
    content()
  }
}