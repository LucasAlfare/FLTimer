package com.lucasalfare.fltimer.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp

private val regularFontFamily = FontFamily(
  Font(
    resource = "JetBrainsMono-Regular.ttf"
  )
)

private val boldFontFamily = FontFamily(
  Font(
    resource = "JetBrainsMono-Bold.ttf"
  )
)

private val italicFontFamily = FontFamily(
  Font(
    resource = "JetBrainsMono-Italic.ttf"
  )
)

data class FLTimerTypography(
  val extraBig: TextStyle = TextStyle(
    fontFamily = boldFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 65.sp
  ),
  val semiBig: TextStyle = TextStyle(
    fontFamily = boldFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 45.sp
  ),
  val h1: TextStyle = TextStyle(
    fontFamily = boldFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 24.sp
  ),
  val subtitle: TextStyle = TextStyle(
    fontFamily = regularFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
  ),
  val body: TextStyle = TextStyle(
    fontFamily = regularFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
  ),
  val button: TextStyle = TextStyle(
    fontFamily = regularFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
  ),
  val caption: TextStyle = TextStyle(
    fontFamily = italicFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
  )
)

internal val LocalFLTimerTypographies = staticCompositionLocalOf { FLTimerTypography() }