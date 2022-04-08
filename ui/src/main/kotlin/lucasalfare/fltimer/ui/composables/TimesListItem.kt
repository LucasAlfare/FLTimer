package lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.data.Solve
import lucasalfare.fltimer.core.toTimestamp
import lucasalfare.fltimer.ui.uiComponentsManager

@Composable
fun TimesListItem(modifier: Modifier = Modifier, index: Int, solve: Solve) {
  Column {
    Text(text = "${index + 1})  Time: ${solve.time.toTimestamp()} | scramble: ${solve.scramble}")
    Row {
      Button(onClick = {
        uiComponentsManager.notifyListeners(AppEvent.SolvesItemRemove, solve.id)
      }) { Text("del") }
    }
  }
}
