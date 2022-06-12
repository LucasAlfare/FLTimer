package lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import lucasalfare.fltimer.core.data.Solve

@Composable
fun TimesListItem(index: Int, solve: Solve) {
  var localFullScreenState by remember { mutableStateOf(FullScreenState.Inactive) }

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clickable {
        localFullScreenState = FullScreenState.Active
      }
      .padding(8.dp)
  ) {
    Text(modifier = Modifier.align(Alignment.CenterStart), text = "${index + 1})")
    Text(
      modifier = Modifier.align(Alignment.Center),
      text = solve.getDisplayableRepresentation(),
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace
    )
  }

  if (localFullScreenState == FullScreenState.Active) {
    val tmpState = LocalMutableFullScreenState.current

    LocalFullScreenComposableReference.current.composableReference = {
      FullScreen(
        innerBoxFillsMaxSize = false
      ) {
        Column(
          modifier = Modifier
            .align(Alignment.Center)
            .background(Color.White)
            .padding(18.dp)
        ) {
          Text(
            text = solve.getDisplayableRepresentation(),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
          )

          TextField(value = solve.scramble, onValueChange = {})

          Button(
            onClick = {
              tmpState.state.value = FullScreenState.Inactive
            }
          ) {
            Text("Dismiss")
          }
        }
      }
    }

    tmpState.state.value = localFullScreenState
    localFullScreenState = FullScreenState.Inactive
  }
}
