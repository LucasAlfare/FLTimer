package com.lucasalfare.fltimer.ui

import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight

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

@Composable
fun BasicSelectableText(
  text: AnnotatedString,
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