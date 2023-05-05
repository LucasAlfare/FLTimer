package com.lucasalfare.fltimer.ui

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputScope
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

suspend fun PointerInputScope.detectTapGestureIfMatch(
  onTap: (Offset) -> Boolean
) {
  awaitEachGesture {
    awaitFirstDown()

    val up = waitForUpOrCancellation()
    if (up != null && onTap(up.position)) {
      up.consume()
    }
  }
}