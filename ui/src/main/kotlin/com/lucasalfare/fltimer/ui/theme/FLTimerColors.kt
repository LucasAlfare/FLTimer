package com.lucasalfare.fltimer.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

class FLTimerColors(
  primary: Color,
  textPrimary: Color,
  textSecondary: Color,
  error: Color,
  isLight: Boolean
) {
  var primary by mutableStateOf(primary)
    private set
  var textPrimary by mutableStateOf(textPrimary)
    private set
  var textSecondary by mutableStateOf(textSecondary)
    private set
  var error by mutableStateOf(error)
    private set
  var isLight by mutableStateOf(isLight)
    internal set

  fun copy(
    primary: Color = this.primary,
    textPrimary: Color = this.textPrimary,
    textSecondary: Color = this.textSecondary,
    error: Color = this.error,
    isLight: Boolean = this.isLight
  ): FLTimerColors = FLTimerColors(
    primary,
    textPrimary,
    textSecondary,
    error,
    isLight
  )

  fun updateColorsFrom(other: FLTimerColors) {
    primary = other.primary
    textPrimary = other.textPrimary
    textSecondary = other.textSecondary
//    background = other.background
    error = other.error
  }
}

private val colorLightPrimary = Color(0xFFFFB400)
private val colorLightTextPrimary = Color(0xFF000000)
private val colorLightTextSecondary = Color(0xFF6C727A)
private val colorLightBackground = Color(0xFFFFFFFF)
private val colorLightError = Color(0xFFD62222)

internal val LocalFLTimerColors = staticCompositionLocalOf { lightColors() }

fun lightColors(
  primary: Color = colorLightPrimary,
  textPrimary: Color = colorLightTextPrimary,
  textSecondary: Color = colorLightTextSecondary,
  background: Color = colorLightBackground,
  error: Color = colorLightError
): FLTimerColors = FLTimerColors(
  primary = primary,
  textPrimary = textPrimary,
  textSecondary = textSecondary,
//  background = background,
  error = error,
  isLight = true
)

fun darkColors(
  primary: Color = colorLightPrimary,
  textPrimary: Color = colorLightTextPrimary,
  textSecondary: Color = colorLightTextSecondary,
  background: Color = colorLightBackground,
  error: Color = colorLightError
): FLTimerColors = FLTimerColors(
  primary = primary,
  textPrimary = textPrimary,
  textSecondary = textSecondary,
//  background = background,
  error = error,
  isLight = false
)