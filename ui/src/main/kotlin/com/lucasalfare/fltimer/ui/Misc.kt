package com.lucasalfare.fltimer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


private const val DividerAlpha = 0.12f

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
fun VerticalDivider(
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.colors.onSurface.copy(alpha = DividerAlpha),
  thickness: Dp = 1.dp
) {
  Box(
    modifier
      .padding(top = 4.dp)
      .fillMaxHeight()
      .width(thickness)
      .background(color = color)
  )
}

/**
 * Custom extension to provide standard tap detection. This function also
 * takes a function from parameter to be called when the tap succeds, if
 * it returns true, of course.
 *
 * Implementation idea from:
 * @see [https://stackoverflow.com/a/76183526/4563960]
 */
suspend fun PointerInputScope.customTapDetect(
  onTap: (Offset) -> Boolean
) {
  awaitEachGesture {
    awaitFirstDown(requireUnconsumed = false)
    val result = waitForUpOrCancellation()
    if (result != null && onTap(result.position)) {
      result.consume()
    }
  }
}