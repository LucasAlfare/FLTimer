package com.lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.data.Penalty
import com.lucasalfare.fltimer.core.data.Solve
import com.lucasalfare.fltimer.ui.WastebasketCharacter
import com.lucasalfare.fltimer.ui.uiManager

@Composable
fun TimesListItem(index: Int, solve: Solve) {
  var expanded by remember { mutableStateOf(false) }
  var txt by remember { mutableStateOf(solve.getDisplayableRepresentation()) }

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
        text = txt,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace
      )

      TextButton(
        modifier = Modifier.align(Alignment.CenterEnd).width(30.dp),
        onClick = {
          uiManager.notifyListeners(FLTimerEvent.SolvesItemRemove, solve.id)
          println("clicked REMOVE")
        }
      ) {
        Text(text = WastebasketCharacter)
      }
    }

    Row(horizontalArrangement = Arrangement.Start) {
      TextButton(
        modifier = Modifier.size(width = 40.dp, height = 30.dp),
        onClick = {
          solve.penalty = Penalty.Ok
          txt = solve.getDisplayableRepresentation()
          uiManager.notifyListeners(FLTimerEvent.SolvesItemUpdate)
          println("clicked OK")
        }
      ) {
        Text(text = "OK", fontSize = 12.sp)
      }

      TextButton(
        modifier = Modifier.size(width = 40.dp, height = 30.dp),
        onClick = {
          solve.penalty = Penalty.PlusTwo
          uiManager.notifyListeners(FLTimerEvent.SolvesItemUpdate)
          txt = solve.getDisplayableRepresentation()
          println("clicked PLUS_TWO")
        }
      ) {
        Text(text = "+2", fontSize = 12.sp)
      }

      TextButton(
        modifier = Modifier.size(width = 50.dp, height = 30.dp),
        onClick = {
          solve.penalty = Penalty.Dnf
          uiManager.notifyListeners(FLTimerEvent.SolvesItemUpdate)
          txt = solve.getDisplayableRepresentation()
          println("clicked DNF")
        }
      ) {
        Text(text = "DNF", fontSize = 12.sp)
      }
    }
  }
}
