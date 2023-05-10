package com.lucasalfare.fltimer.ui.screens.solves

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.model.Penalty
import com.lucasalfare.fltimer.core.model.Solve
import com.lucasalfare.fltimer.ui.BasicSelectableText
import com.lucasalfare.fltimer.ui.theme.FLTimerTheme
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
      Text(
        text = "${index + 1})",
        modifier = Modifier.align(Alignment.CenterStart),
        style = FLTimerTheme.typography.caption
      )

      Text(
        modifier = Modifier.align(Alignment.Center),
        text = solve.getDisplayableRepresentation(),
        style = FLTimerTheme.typography.body
      )

      TextButton(
        modifier = Modifier.align(Alignment.CenterEnd).width(30.dp),
        onClick = {
          uiManager.notifyListeners(FLTimerEvent.SolvesItemRemove, solve)
        }
      ) {
        Text(
          text = "x",
          style = FLTimerTheme.typography.button
        )
      }
    }

    Row(horizontalArrangement = Arrangement.Start) {
      PenaltyButton(solve, Penalty.Ok)
      PenaltyButton(solve, Penalty.PlusTwo)
      PenaltyButton(solve, Penalty.Dnf)
    }

    if (expanded) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = "Scramble:",
          style = FLTimerTheme.typography.subtitle
        )

        Spacer(Modifier.width(8.dp))

        BasicSelectableText(
          text = solve.scramble,
          textStyle = FLTimerTheme.typography.body
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
    Text(
      text = nextPenalty.toString(),
      style = FLTimerTheme.typography.button.copy(fontSize = 12.sp)
    )
  }
}