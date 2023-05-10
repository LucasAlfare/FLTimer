package com.lucasalfare.fltimer.ui.screens.timer

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lucasalfare.fltimer.core.model.FLTimerState
import com.lucasalfare.fltimer.ui.BasicSelectableText
import com.lucasalfare.fltimer.ui.theme.FLTimerTheme

@Composable
fun Scramble() {
  val scramble = remember { FLTimerState.currentScramble }

  BasicSelectableText(
    text = scramble.value,
    modifier = Modifier.padding(12.dp),
    textStyle = FLTimerTheme.typography.h1.copy(textAlign = TextAlign.Center)
  )
}