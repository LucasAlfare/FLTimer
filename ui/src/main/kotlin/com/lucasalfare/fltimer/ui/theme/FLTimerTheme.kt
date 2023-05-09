package com.lucasalfare.fltimer.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember

object FLTimerTheme {

  val colors: FLTimerColors
    @Composable
    @ReadOnlyComposable
    get() = LocalFLTimerColors.current

  val typography: FLTimerTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalFLTimerTypographies.current

  val dimensions: FLTimerDimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalFLTimerDimensions.current
}

@Composable
fun FLTimerTheme(
  colors: FLTimerColors = FLTimerTheme.colors,
  typography: FLTimerTypography = FLTimerTheme.typography,
  dimensions: FLTimerDimensions = FLTimerTheme.dimensions,
  content: @Composable () -> Unit
) {
  // creating a new object for colors to not mutate the initial colors set when updating the values
  val rememberedColors = remember { colors.copy() }.apply { updateColorsFrom(colors) }
  CompositionLocalProvider(
    LocalFLTimerColors provides rememberedColors,
    LocalFLTimerTypographies provides typography,
    LocalFLTimerDimensions provides dimensions
  ) {
    content()
  }
}