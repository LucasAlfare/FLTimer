package com.lucasalfare.fltimer.ui

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.lucasalfare.fltimer.core.model.FLTimerModel
import com.lucasalfare.fltimer.core.toTimestamp

@Composable
fun Display() {
  val displayText = remember { FLTimerModel.currentDisplayValue }

  Text(
    text = displayText.value,
    fontSize = 65.sp,
    fontFamily = FontFamily.Monospace,
    fontWeight = FontWeight.Bold
  )
}