package com.lucasalfare.fltimer.ui.raw

import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.lucasalfare.fltimer.ui.theme.FLTimerTheme

@Composable
fun FLTimerTextButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  textStyle: TextStyle = FLTimerTheme.typography.button,
  buttonColor: Color = FLTimerTheme.colors.secondary,
  textColor: Color = FLTimerTheme.colors.textOnSecondary
) {
  TextButton(
    onClick = { onClick() },
    modifier = modifier
  ) {
    Text(
      text = text,
      color = textColor,
      style = textStyle
    )
  }
}