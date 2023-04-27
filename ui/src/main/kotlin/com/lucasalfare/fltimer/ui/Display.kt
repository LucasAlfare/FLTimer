package com.lucasalfare.fltimer.ui

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.lucasalfare.fltimer.core.model.FLTimerModel
import com.lucasalfare.fltimer.core.toTimestamp
import uiManager

@Composable
fun Display() {
  val displayValue = remember { FLTimerModel.currentDisplayValue }

  Text(
    text = displayValue.value.toTimestamp(),
    fontSize = 65.sp,
    fontFamily = FontFamily.Monospace,
    fontWeight = FontWeight.Bold
  )
}