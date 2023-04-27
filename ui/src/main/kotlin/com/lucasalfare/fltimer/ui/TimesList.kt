package com.lucasalfare.fltimer.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.model.FLTimerModel
import com.lucasalfare.fltimer.core.model.Penalty
import com.lucasalfare.fltimer.core.model.Solve
import uiManager

@Composable
fun TimesList() {
  val solves = remember { FLTimerModel.getCurrentActiveSession().solves }

  // list scrolling management
  val lazyListState = rememberLazyListState()

  Column(
    modifier = Modifier
      .shadow(4.dp)
      .padding(8.dp)
      .fillMaxWidth()
      .fillMaxHeight(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Column(modifier = Modifier.padding(8.dp)) {
      Text(text = "Number of solves: ${solves.size}", fontSize = 12.sp)

      TextButton(onClick = {
        uiManager.notifyListeners(event = FLTimerEvent.SolvesClear, origin = this)
      }) {
        Text("Clear")
      }
      LazyColumn(
        modifier = Modifier
          .border(
            width = 1.dp,
            shape = RoundedCornerShape(5.dp),
            color = Color.LightGray
          ), state = lazyListState
      ) {
        solves.forEachIndexed { index, solve ->
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

@Composable
fun TimesListItem(index: Int, solve: Solve) {
  var expanded by remember { mutableStateOf(false) }

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple()
      ) {
        expanded = !expanded
        println(expanded)
      }
      .padding(12.dp)
  ) {
    Box(modifier = Modifier.fillMaxWidth()) {
      Text(modifier = Modifier.align(Alignment.CenterStart), text = "${index + 1})")
      Text(
        modifier = Modifier.align(Alignment.Center),
        text = solve.getDisplayableRepresentation(),
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace
      )

      TextButton(
        modifier = Modifier.align(Alignment.CenterEnd).width(30.dp),
        onClick = {
          uiManager.notifyListeners(FLTimerEvent.SolvesItemRemove, solve)
          println("clicked REMOVE")
        }
      ) {
        Text(text = "x")
      }
    }

    Row(horizontalArrangement = Arrangement.Start) {
      TextButton(
        modifier = Modifier.size(width = 40.dp, height = 30.dp),
        onClick = {
          uiManager.notifyListeners(FLTimerEvent.SolveItemPenaltyUpdate, arrayOf(solve, Penalty.Ok))
        }
      ) {
        Text(text = "OK", fontSize = 12.sp)
      }

      TextButton(
        modifier = Modifier.size(width = 40.dp, height = 30.dp),
        onClick = {
          uiManager.notifyListeners(FLTimerEvent.SolveItemPenaltyUpdate, arrayOf(solve, Penalty.PlusTwo))
        }
      ) {
        Text(text = "+2", fontSize = 12.sp)
      }

      TextButton(
        modifier = Modifier.size(width = 50.dp, height = 30.dp),
        onClick = {
          uiManager.notifyListeners(FLTimerEvent.SolveItemPenaltyUpdate, arrayOf(solve, Penalty.Dnf))
        }
      ) {
        Text(text = "DNF", fontSize = 12.sp)
      }
    }
  }
}