package com.lucasalfare.fltimer.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class FLTimerTypography(
  val extraBig: TextStyle = TextStyle(
    fontFamily = MyFontFamilies.regular,
    fontWeight = FontWeight.Normal,
    fontSize = 65.sp
  ),
  val semiBig: TextStyle = TextStyle(
    fontFamily = MyFontFamilies.bold,
    fontWeight = FontWeight.Normal,
    fontSize = 45.sp
  ),
  val h1: TextStyle = TextStyle(
    fontFamily = MyFontFamilies.bold,
    fontWeight = FontWeight.Normal,
    fontSize = 24.sp
  ),
  val subtitle: TextStyle = TextStyle(
    fontFamily = MyFontFamilies.regular,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
  ),
  val body: TextStyle = TextStyle(
    fontFamily = MyFontFamilies.regular,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
  ),
  val button: TextStyle = TextStyle(
    fontFamily = MyFontFamilies.regular,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
  ),
  val caption: TextStyle = TextStyle(
    fontFamily = MyFontFamilies.italic,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
  )
)

val LocalFLTimerTypographies = staticCompositionLocalOf { FLTimerTypography() }