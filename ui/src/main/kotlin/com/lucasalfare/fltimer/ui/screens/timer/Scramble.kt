package com.lucasalfare.fltimer.ui.screens.timer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lucasalfare.fltimer.core.model.FLTimerState
import com.lucasalfare.fltimer.ui.BasicSelectableText

@Composable
fun Scramble() {
  val scramble = remember { FLTimerState.currentScramble }

  BasicSelectableText(
    text = scramble.value,
    modifier = Modifier.padding(12.dp),
    textStyle = TextStyle(
      fontSize = 20.sp,
      fontWeight = FontWeight.Bold,
      textAlign = TextAlign.Center,
      fontFamily = FontFamily.Monospace
    )
  )
}