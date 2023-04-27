package com.lucasalfare.fltimer.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.lucasalfare.fltimer.core.model.FLTimerModel

@Composable
fun Scramble() {
  val scramble = remember { FLTimerModel.currentScramble }

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(100.dp)
      //.defaultMinSize(minWidth = 200.dp, minHeight = 70.dp)
      .padding(12.dp)
  ) {
    Text(
      modifier = Modifier.align(Alignment.Center),
      text = scramble.value,
      fontSize = 20.sp,
      fontWeight = FontWeight.Bold,
      textAlign = TextAlign.Center,
      fontFamily = FontFamily.Monospace
    )
  }
}