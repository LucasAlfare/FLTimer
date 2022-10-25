package lucasalfare.fltimer.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import lucasalfare.fltimer.ui.composables.*


@Composable
fun DefaultDesktopApp() {
  Row {
    Box(
      modifier = Modifier
        .width(180.dp)
        .weight(1f)
        .padding(bottom = 8.dp)
    ) {
      Column {
        SessionController()
        TimesList()
      }
    }

    Box(modifier = Modifier.weight(2f)) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.weight(1f)) {
          Scramble()
        }

        Box(modifier = Modifier.weight(2f), contentAlignment = Alignment.TopCenter) {
          Display()
        }
      }
    }

    Box(
      modifier = Modifier
        .fillMaxHeight()
        .width(180.dp)
        .weight(1f)
        .padding(bottom = 8.dp)
    ) {
      StatisticsList()
    }
  }


  /**
  Box(modifier = Modifier
  .fillMaxSize()
  ) {
  Box(
  Modifier
  .align(Alignment.TopCenter)
  .width(300.dp)
  .padding(12.dp)
  .background(Color.Green)
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
   **/
}
