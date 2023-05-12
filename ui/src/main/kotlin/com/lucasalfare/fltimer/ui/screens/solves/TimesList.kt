package com.lucasalfare.fltimer.ui.screens.solves

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.model.FLTimerState
import com.lucasalfare.fltimer.ui.raw.FLTimerText
import com.lucasalfare.fltimer.ui.raw.FLTimerTextButton
import com.lucasalfare.fltimer.ui.theme.FLTimerTheme
import com.lucasalfare.fltimer.ui.uiManager

@Composable
fun TimesList() {
  // list scrolling management
  val lazyListState = rememberLazyListState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(12.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Column(modifier = Modifier.padding(8.dp)) {
      FLTimerText(
        text = "Number of solves: ${FLTimerState.getCurrentActiveSession().solves.size}",
        style = FLTimerTheme.typography.caption
      )

      // TODO: move this button to bottom of the lazy list
      FLTimerTextButton(
        text = "Clear",
        modifier = Modifier.weight(0.2f).fillMaxWidth(),
        onClick = {
          uiManager.notifyListeners(event = FLTimerEvent.SolvesClear, origin = this)
        }
      )

      LazyColumn(
        modifier = Modifier
          .weight(1f)
          .border(
            width = 1.dp,
            shape = RoundedCornerShape(5.dp),
            color = FLTimerTheme.colors.secondary.copy(alpha = 0.8f)
          ),
        state = lazyListState
      ) {
        FLTimerState.getCurrentActiveSession().solves.forEachIndexed { index, solve ->
          item {
            TimesListItem(
              index = index,
              solve = solve
            )
            Divider(startIndent = 5.dp, modifier = Modifier.padding(end = 5.dp))
          }
        }
      }
    }
  }
}