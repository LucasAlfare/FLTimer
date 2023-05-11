package com.lucasalfare.fltimer.ui.screens.solves

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
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
import com.lucasalfare.fltimer.ui.raw.FLTimerButton
import com.lucasalfare.fltimer.ui.raw.FLTimerText
import com.lucasalfare.fltimer.ui.raw.FLTimerTextButton
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
      FLTimerText(
        text = "${index + 1})",
        modifier = Modifier.align(Alignment.CenterStart),
        style = FLTimerTheme.typography.caption,
      )

      FLTimerText(
        text = solve.getDisplayableRepresentation(),
        modifier = Modifier.align(Alignment.Center),
      )

      FLTimerTextButton(
        text = "x",
        modifier = Modifier.align(Alignment.CenterEnd).width(30.dp),
        onClick = { uiManager.notifyListeners(FLTimerEvent.SolvesItemRemove, solve) }
      )
    }

    Row(horizontalArrangement = Arrangement.Start) {
      PenaltyButton(solve, Penalty.Ok)
      PenaltyButton(solve, Penalty.PlusTwo)
      PenaltyButton(solve, Penalty.Dnf)
    }

    if (expanded) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        FLTimerText(text = "Scramble", style = FLTimerTheme.typography.subtitle)

        Spacer(Modifier.width(8.dp))

        BasicSelectableText(text = solve.scramble)
      }
    }
  }
}

@Composable
private fun PenaltyButton(targetSolve: Solve, nextPenalty: Penalty) {
  FLTimerTextButton(
    text = nextPenalty.toString(),
    textStyle = FLTimerTheme.typography.button.copy(fontSize = 12.sp),
    modifier = Modifier.size(width = 50.dp, height = 30.dp),
    onClick = { uiManager.notifyListeners(FLTimerEvent.SolveItemPenaltyUpdate, arrayOf(targetSolve, nextPenalty)) }
  )
}