package lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.data.Solve
import lucasalfare.fltimer.core.toTimestamp
import lucasalfare.fltimer.ui.WastebasketCharacter
import lucasalfare.fltimer.ui.uiComponentsManager

@Composable
fun TimesListItem(modifier: Modifier = Modifier, index: Int, solve: Solve) {
  Box(Modifier.fillMaxWidth()) {
    Text(modifier = Modifier.align(Alignment.CenterStart), text = "${index + 1})  Time: ${solve.time.toTimestamp()}")

    TextButton(
      modifier = Modifier.align(Alignment.CenterEnd),
      onClick = {
        uiComponentsManager.notifyListeners(AppEvent.SolvesItemRemove, solve.id)
      }
    ) {
      Text(WastebasketCharacter)
    }
  }
}
