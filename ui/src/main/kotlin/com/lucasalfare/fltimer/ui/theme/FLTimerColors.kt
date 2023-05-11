package com.lucasalfare.fltimer.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

class FLTimerColors(
  primary: Color,
  secondary: Color,
  background: Color,
  textOnPrimary: Color,
  textoOnSecondary: Color,
  error: Color,
  isLight: Boolean
) {
  var primary by mutableStateOf(primary)
    private set
  var secondary by mutableStateOf(secondary)
    private set
  var background by mutableStateOf(background)
    private set
  var textOnPrimary by mutableStateOf(textOnPrimary)
    private set
  var textOnSecondary by mutableStateOf(textoOnSecondary)
    private set
  var error by mutableStateOf(error)
    private set
  var isLight by mutableStateOf(isLight)
    internal set

  fun copy(
    primary: Color = this.primary,
    secondary: Color = this.secondary,
    background: Color = this.background,
    textOnPrimary: Color = this.textOnPrimary,
    textOnSecondary: Color = this.textOnSecondary,
    error: Color = this.error,
    isLight: Boolean = this.isLight
  ): FLTimerColors = FLTimerColors(
    primary,
    secondary,
    background,
    textOnPrimary,
    textOnSecondary,
    error,
    isLight
  )

  fun updateColorsFrom(other: FLTimerColors) {
    primary = other.primary
    secondary = other.secondary
    background = other.background
    textOnPrimary = other.textOnPrimary
    textOnSecondary = other.textOnSecondary
    error = other.error
  }
}

val LocalFLTimerColors = staticCompositionLocalOf { lightColors() }

fun lightColors(
  primary: Color = Color(0xFFFFB400),
  secondary: Color = Color(0xFF8B2635),
  background: Color = Color(0xFF2E3532),
  textOnPrimary: Color = Color(0xFFD2D4C8),
  textOnSecondary: Color = Color(0xFFE0E2DB),
  error: Color = Color(0xFFC36F09)
): FLTimerColors = FLTimerColors(
  primary = primary,
  secondary = secondary,
  background = background,
  textOnPrimary = textOnPrimary,
  textoOnSecondary = textOnSecondary,
  error = error,
  isLight = true
)

fun darkColors(
  primary: Color = Color(0xFFFFB400),
  secondary: Color = Color(0xFF8B2635),
  background: Color = Color(0xFF2E3532),
  textOnPrimary: Color = Color(0xFFD2D4C8),
  textOnSecondary: Color = Color(0xFFE0E2DB),
  error: Color = Color(0xFFC36F09)
): FLTimerColors = FLTimerColors(
  primary = primary,
  secondary = secondary,
  background = background,
  textOnPrimary = textOnPrimary,
  textoOnSecondary = textOnSecondary,
  error = error,
  isLight = false
)