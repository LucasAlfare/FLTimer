package com.lucasalfare.fltimer.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class FLTimerDimensions(
  val paddingDiscrete: Dp = 2.dp,
  val paddingSmall: Dp = 4.dp,
  val paddingMedium: Dp = 8.dp,
  val paddingLarge: Dp = 24.dp
)

internal val LocalFLTimerDimensions = staticCompositionLocalOf { FLTimerDimensions() }