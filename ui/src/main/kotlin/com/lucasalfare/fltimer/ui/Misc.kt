package com.lucasalfare.fltimer.ui

import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun BasicSelectableText(
  text: String,
  textStyle: TextStyle = TextStyle(),
  modifier: Modifier = Modifier
) {
  SelectionContainer {
    Text(
      text = text,
      style = textStyle,
      modifier = modifier
    )
  }
}