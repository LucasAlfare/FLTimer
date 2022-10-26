package lucasalfare.fltimer.ui.composables

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
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.data.Penalty
import lucasalfare.fltimer.core.data.Solve
import lucasalfare.fltimer.ui.WastebasketCharacter
import lucasalfare.fltimer.ui.uiManager

@Composable
fun TimesListItem(index: Int, solve: Solve, solveDisplayableRepresentation: String) {
  var expanded by remember { mutableStateOf(false) }
  var txt by remember { mutableStateOf(solveDisplayableRepresentation) }

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
      .padding(
        start = 12.dp,
        end = 12.dp,
        top = 24.dp,
        bottom = 24.dp
      )
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
        modifier = Modifier.size(width = 40.dp, height = 30.dp).align(Alignment.CenterEnd),
        onClick = {
          uiManager.notifyListeners(AppEvent.SolvesItemRemove, solve.id)
          println("clicked REMOVE")
        }
      ) {
        Text(text = WastebasketCharacter, fontSize = 8.sp)
      }
    }

    Row(horizontalArrangement = Arrangement.Start) {
      TextButton(
        modifier = Modifier.size(width = 35.dp, height = 30.dp),
        onClick = {
          solve.penalty = Penalty.Ok
          txt = solve.getDisplayableRepresentation()
          uiManager.notifyListeners(AppEvent.SolvesItemUpdate)
          println("clicked OK")
        }
      ) {
        Text(text = "OK", fontSize = 8.sp)
      }

      TextButton(
        modifier = Modifier.size(width = 35.dp, height = 30.dp),
        onClick = {
          solve.penalty = Penalty.PlusTwo
          uiManager.notifyListeners(AppEvent.SolvesItemUpdate)
          txt = solve.getDisplayableRepresentation()
          println("clicked PLUS_TWO")
        }
      ) {
        Text(text = "+2", fontSize = 8.sp)
      }

      TextButton(
        modifier = Modifier.size(width = 40.dp, height = 30.dp),
        onClick = {
          solve.penalty = Penalty.Dnf
          uiManager.notifyListeners(AppEvent.SolvesItemUpdate)
          txt = solve.getDisplayableRepresentation()
          println("clicked DNF")
        }
      ) {
        Text(text = "DNF", fontSize = 8.sp)
      }
    }

    /*
    Text(modifier = Modifier.align(Alignment.CenterStart), text = "${index + 1})")
    Text(
      modifier = Modifier.align(Alignment.Center),
      text = solve.getDisplayableRepresentation(),
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace
    )

    if (expanded) {

    }
     */
  }
}
