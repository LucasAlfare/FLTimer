package com.lucasalfare.fltimer.ui.screens.timer

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lucasalfare.fltimer.core.model.FLTimerState

@Composable
fun Scramble() {
  val scramble = remember { FLTimerState.currentScramble }

  Text(
    modifier = Modifier
      .padding(12.dp),
    text = scramble.value,
    fontSize = 20.sp,
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center,
    fontFamily = FontFamily.Monospace
  )
}