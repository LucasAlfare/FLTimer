package lucasalfare.fltimer.desktop

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import lucasalfare.fltimer.ui.composables.*


@Composable
fun DefaultDesktopApp() {
  Box(modifier = Modifier
    .fillMaxSize()
  ) {
    Box(
      Modifier
        .align(Alignment.TopCenter)
        .width(300.dp)
        .padding(12.dp)
      //.background(Color.Green)
    ) {
      Scramble()
    }

    Box(
      Modifier
        .align(Alignment.Center)
        .padding(12.dp)
      //.background(Color.Yellow)
    ) {
      Display()
    }

    Box(
      Modifier
        .fillMaxHeight()
        .align(Alignment.TopStart)
        .width(200.dp)
        .padding(12.dp)
      //.background(Color.Cyan)
    ) {
      Column {
        SessionController()
        TimesList()
      }
    }

    Box(
      Modifier
        .align(Alignment.TopEnd)
        .width(200.dp)
        .fillMaxHeight()
        .padding(12.dp)
      //.background(Color.Red)
    ) {
      StatisticsList()
    }
  }
}
