package com.lucasalfare.fltimer.ui.screens.solves

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.sp
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.model.FLTimerState
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
      Text(
        text = "Number of solves: ${FLTimerState.getCurrentActiveSession().solves.size}",
        fontSize = 12.sp
      )

      LazyColumn(
        modifier = Modifier
          .weight(0.8f)
          .border(
            width = 1.dp,
            shape = RoundedCornerShape(5.dp),
            color = Color.LightGray
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

    TextButton(
      modifier = Modifier.weight(0.2f),
      onClick = {
        uiManager.notifyListeners(event = FLTimerEvent.SolvesClear, origin = this)
      }
    ) {
      Text("Clear")
    }
  }
}