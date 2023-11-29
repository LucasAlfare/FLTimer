package com.lucasalfare.fltimer.ui.raw

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.lucasalfare.fltimer.ui.theme.FLTimerTheme

@Composable
fun FLTimerButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  textStyle: TextStyle = FLTimerTheme.typography.button,
  buttonColor: Color = FLTimerTheme.colors.secondary,
  textColor: Color = FLTimerTheme.colors.textOnSecondary
) {
  Button(
    onClick = { onClick() },
    modifier = modifier,
    enabled = enabled,
    colors = ButtonDefaults.buttonColors(
      backgroundColor = buttonColor,
      contentColor = textColor
    )
  ) {
    Text(
      text = text,
      style = textStyle
    )
  }
}