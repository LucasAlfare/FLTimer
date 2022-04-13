package lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.data.Penalty
import lucasalfare.fltimer.core.data.Solve
import lucasalfare.fltimer.core.toTimestamp
import lucasalfare.fltimer.ui.WarningCharacter
import lucasalfare.fltimer.ui.WastebasketCharacter
import lucasalfare.fltimer.ui.uiComponentsManager

@Composable
fun TimesListItem(index: Int, solve: Solve) {
  var showMenu by remember { mutableStateOf(false) }
  // dialog
  var isOpen by remember { mutableStateOf(false) }

  Box(Modifier.fillMaxWidth().clickable {
    showMenu = !showMenu
  }) {
    Text(modifier = Modifier.align(Alignment.CenterStart), text = "${index + 1})")
    Text(
      modifier = Modifier.align(Alignment.Center), text = when (solve.penalty) {
        Penalty.PlusTwo -> "+${(solve.time + 2000).toTimestamp()}"
        Penalty.Dnf -> "DNF"
        else -> solve.time.toTimestamp()
      }
    )

    DropdownMenu(
      expanded = showMenu,
      onDismissRequest = { showMenu = false }
    ) {
      Column {
        Column(Modifier.fillMaxWidth()) {
          Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            val scramble by rememberSaveable { mutableStateOf(solve.scramble.ifEmpty { "- -" }) }
            Text(buildAnnotatedString {
              withStyle(
                SpanStyle(
                  fontWeight = FontWeight.Bold
                )
              ) {
                append("Scramble:")
              }
            })

            TextField(
              value = scramble,
              onValueChange = { /*pass*/ }
            )
          }

          Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            var comment by rememberSaveable { mutableStateOf(solve.comment.ifEmpty { "- -" }) }

            Text(buildAnnotatedString {
              withStyle(
                SpanStyle(
                  fontWeight = FontWeight.Bold
                )
              ) {
                append("Comment:")
              }
            })

            TextField(
              value = comment,
              onValueChange = {
                comment = it
                //automatically updates data
                solve.comment = comment
              }
            )
          }

          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            TextButton(onClick = {
              solve.penalty = Penalty.Ok
              showMenu = false
            }) { Text("OK") }

            TextButton(onClick = {
              solve.penalty = Penalty.PlusTwo
              showMenu = false
            }) { Text("+2") }

            TextButton(onClick = {
              solve.penalty = Penalty.Dnf
              showMenu = false
            }) { Text("DNF") }
          }
        }

        Row {
          Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
              backgroundColor = Color(0xffef5350),
              contentColor = Color.White
            ),
            onClick = {
              isOpen = true
              showMenu = false
            }
          ) { Text("delete $WastebasketCharacter") }
        }
      }
    }

    if (isOpen) {
      Dialog(
        modifier = Modifier,
        title = "Warning $WarningCharacter",
        bodyText = "Delete this time?",
        dismiss = { isOpen = false },
        confirmationCallback = {
          uiComponentsManager.notifyListeners(AppEvent.SolvesItemRemove, solve.id)
        }
      )
    }

//    TextButton(
//      modifier = Modifier.align(Alignment.CenterEnd),
//      onClick = {
//        uiComponentsManager.notifyListeners(AppEvent.SolvesItemRemove, solve.id)
//      }
//    ) {
//      Text(WastebasketCharacter)
//    }
  }
}
