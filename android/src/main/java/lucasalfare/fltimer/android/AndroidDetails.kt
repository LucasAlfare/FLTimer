package lucasalfare.fltimer.android

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import lucasalfare.fltimer.ui.composables.SessionController
import lucasalfare.fltimer.ui.composables.TimesList

@Composable
fun AndroidDetails() {
  var hidden by remember { mutableStateOf(true) }

  Box(
    modifier = Modifier
      .then(
        if (hidden)
          Modifier.size(0.dp)
        else
          Modifier.fillMaxSize()
      )
      .animateContentSize()
  ) {
    ExpandedDetails(onToggle = { hidden = true })
  }

  Box(
    modifier = Modifier
      .then(
        if (hidden)
          Modifier.fillMaxSize()
        else
          Modifier.size(0.dp)
      )
      .animateContentSize()
  ) {
    Box(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .fillMaxWidth()
        .clickable {
          hidden = false
        }
    ) {
      Divider(
        thickness = 8.dp,
        modifier = Modifier
          .padding(start = 48.dp, end = 48.dp, top = 16.dp, bottom = 16.dp)
          .clip(RoundedCornerShape(5.dp))
      )
    }
  }
}

/**
 * TODO: make back press button do `onToggle()`
 */
@Composable
private fun ExpandedDetails(onToggle: () -> Unit = {}) {
  val interactionSource = remember { MutableInteractionSource() }
  var showingSolves by remember { mutableStateOf(true) }

  // outer
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color.Gray.copy(alpha = 0.5f))
      .clickable(
        indication = null,
        interactionSource = interactionSource,
      ) {
        onToggle()
      }
  ) {
    // inner
    Box(
      modifier = Modifier
        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
        .align(Alignment.BottomStart)
        .background(Color.White)
        .clickable(
          indication = null,
          interactionSource = interactionSource,
          onClick = { /*pass*/ }
        )
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .padding(8.dp)
      ) {
        Row {
          Button(
            onClick = { showingSolves = true },
            enabled = !showingSolves
          ) {
            Text(text = "Times")
          }

          Button(
            onClick = { showingSolves = false },
            enabled = showingSolves
          ) {
            Text(text = "Stats")
          }
        }

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
        ) {
          Box(
            modifier = Modifier
              .then(
                if (showingSolves)
                  Modifier.wrapContentSize()
                else
                  Modifier.size(0.dp)
              )
              .animateContentSize()
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              SessionController()
              TimesList()
            }
          }

          Box(
            modifier = Modifier
              .then(
                if (!showingSolves)
                  Modifier.wrapContentSize()
                else
                  Modifier.size(0.dp)
              )
              .animateContentSize()
          ) {
            Text(text = "BILUUUUUUUUUUUU")
          }
        }
      }
    }
  }
}
