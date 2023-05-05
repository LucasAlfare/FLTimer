package com.lucasalfare.fltimer.ui.screens.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.awaitApplication
import com.lucasalfare.fltimer.ui.detectTapGestureIfMatch
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.awaitCancellation

val myAnnotatedString = buildAnnotatedString {
  val word = "tristeza"

  append(word)

  addStringAnnotation(tag = "risos", annotation = "marcador", start = 0, end = word.length)
}

@Composable
fun StatsScreen() {
  LazyColumn(Modifier.fillMaxSize()) {
    item {
      SelectionContainer(
        modifier = Modifier
          .fillMaxSize()
      ) {

        Box {
          ClickableText(
            text = myAnnotatedString,
            onClick = {
              println("click from clickable text. Value of it: $it")
            },
            modifier = Modifier
              .pointerInput(Unit) {
                awaitEachGesture {
                  awaitFirstDown(requireUnconsumed = false)
                  val result = waitForUpOrCancellation()
                  if (result != null) {
                    println("asdkjfhaslkdfh!!! NO TEXTO AGORA")
                  }
                }
              }
          )
        }
      }
    }
  }
}

