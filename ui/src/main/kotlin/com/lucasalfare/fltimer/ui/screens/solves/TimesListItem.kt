package com.lucasalfare.fltimer.ui.screens.solves

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.model.Penalty
import com.lucasalfare.fltimer.core.model.Solve
import com.lucasalfare.fltimer.ui.uiManager

@Composable
fun TimesListItem(index: Int, solve: Solve) {
  var expanded by remember { mutableStateOf(false) }

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(4.dp)
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple()
      ) { expanded = !expanded }
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
        }
      ) { Text(text = "x") }
    }

    Row(horizontalArrangement = Arrangement.Start) {
      PenaltyButton(solve, Penalty.Ok)
      PenaltyButton(solve, Penalty.PlusTwo)
      PenaltyButton(solve, Penalty.Dnf)
    }

    if (expanded) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Scramble:")

        Spacer(Modifier.width(8.dp))

        TextField(
          value = solve.scramble,
          textStyle = TextStyle(textAlign = TextAlign.Center),
          onValueChange = { /*pass*/ }
        )
      }
    }
  }
}

@Composable
private fun PenaltyButton(targetSolve: Solve, nextPenalty: Penalty) {
  TextButton(
    modifier = Modifier.size(width = 50.dp, height = 30.dp),
    onClick = {
      uiManager.notifyListeners(FLTimerEvent.SolveItemPenaltyUpdate, arrayOf(targetSolve, nextPenalty))
    }
  ) {
    Text(text = nextPenalty.toString(), fontSize = 12.sp)
  }
}