package com.lucasalfare.fltimer.ui.raw

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.lucasalfare.fltimer.ui.theme.FLTimerTheme

@Composable
fun FLTimerText(
  text: String,
  modifier: Modifier = Modifier,
  style: TextStyle = FLTimerTheme.typography.body,
  textAlign: TextAlign = TextAlign.Start,
  color: Color = FLTimerTheme.colors.textOnPrimary
) {
  Text(
    text = text,
    modifier = modifier,
    style = style,
    color = color
  )
}

@Composable
fun FLTimerText(
  text: AnnotatedString,
  onTextLayout: (TextLayoutResult) -> Unit = {},
  modifier: Modifier = Modifier,
  style: TextStyle = FLTimerTheme.typography.body,
  color: Color = FLTimerTheme.colors.textOnPrimary
) {
  Text(
    text = text,
    onTextLayout = { onTextLayout(it) },
    modifier = modifier,
    style = style,
    color = color
  )
}