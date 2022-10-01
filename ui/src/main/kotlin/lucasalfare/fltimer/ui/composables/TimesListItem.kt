package lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import lucasalfare.fltimer.core.data.Solve

@Composable
fun TimesListItem(index: Int, solve: Solve) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clickable {

      }
      .padding(
        start = 12.dp,
        end = 12.dp,
        top = 24.dp,
        bottom = 24.dp
      )
  ) {
    Text(modifier = Modifier.align(Alignment.CenterStart), text = "${index + 1})")
    Text(
      modifier = Modifier.align(Alignment.Center),
      text = solve.getDisplayableRepresentation(),
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace
    )
  }
}
