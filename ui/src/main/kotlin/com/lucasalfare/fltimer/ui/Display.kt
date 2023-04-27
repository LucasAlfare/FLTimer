package com.lucasalfare.fltimer.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp
import com.lucasalfare.fltimer.core.model.FLTimerModel
import com.lucasalfare.fltimer.core.toTimestamp

@Composable
fun Display() {
  val displayValue = remember { FLTimerModel.currentDisplayValue }

  Text(text = displayValue.value.toTimestamp(), fontSize = 45.sp)
}