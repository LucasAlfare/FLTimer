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
fun DesktopApp() {
  Box(modifier = Modifier.fillMaxSize()) {
    Box(Modifier.align(Alignment.TopCenter)) {
      Scramble()
    }

    Box(Modifier.align(Alignment.Center)) {
      Display()
    }

    Box(Modifier.fillMaxHeight().width(200.dp)) {
      Column {
        SessionController()
        TimesList()
      }
    }

    Box(Modifier.align(Alignment.TopEnd).width(200.dp).fillMaxHeight()) {
      StatisticsList()
    }
  }
}