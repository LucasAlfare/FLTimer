package com.lucasalfare.fltimer.ui.screens.stats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import com.lucasalfare.fltimer.ui.customTapDetect
import com.lucasalfare.fltimer.ui.raw.FLTimerText

@Composable
fun BasicSelectableAndClickableText(
  text: AnnotatedString,
  containerModifier: Modifier = Modifier,
  onAnnotationItemClick: (AnnotatedString.Range<String>) -> Unit = {}
) {
  SelectionContainer(
    modifier = containerModifier
  ) {
    Box {
      val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }

      FLTimerText(
        text = text,
        onTextLayout = { layoutResult.value = it },
        modifier = Modifier
          .pointerInput(Unit) {
            customTapDetect { offsetPosition ->
              val annotation = layoutResult.value?.getOffsetForPosition(offsetPosition)?.let { offset ->
                text.getStringAnnotations(
                  start = offset,
                  end = offset,
                  tag = StatisticsTemplateProvider.STATISTIC_TAG_LABEL,
                ).firstOrNull()
              }

              if (annotation != null) {
                onAnnotationItemClick(annotation)
                true
              } else {
                false
              }
            }
          }
      )

//      Text(
//        text = text,
//        onTextLayout = { layoutResult.value = it },
//        modifier = Modifier
//          .pointerInput(Unit) {
//            customTapDetect { offsetPosition ->
//              val annotation = layoutResult.value?.getOffsetForPosition(offsetPosition)?.let { offset ->
//                text.getStringAnnotations(
//                  start = offset,
//                  end = offset,
//                  tag = StatisticsTemplateProvider.STATISTIC_TAG_LABEL,
//                ).firstOrNull()
//              }
//
//              if (annotation != null) {
//                onAnnotationItemClick(annotation)
//                true
//              } else {
//                false
//              }
//            }
//          }
//      )
    }
  }
}