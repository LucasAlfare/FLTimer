import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lucasalfare.fltimer.ui.screens.timer.Display
import com.lucasalfare.fltimer.ui.FLTimerUiState
import com.lucasalfare.fltimer.ui.screens.timer.Scramble
import com.lucasalfare.fltimer.ui.screens.solves.TimesList

@Composable
fun App() {
  val showingSolves = remember { FLTimerUiState.showingSolves }
  Row {
    Box(
      modifier = Modifier
        .padding(8.dp)
    ) {
      Column {
        Button(onClick = { showingSolves.value = !showingSolves.value }) {
          Text("solves")
        }
        if (showingSolves.value) {
          TimesList()
        }
      }
    }

    Box {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.weight(1f)) {
          Scramble()
        }

        Box(
          modifier = Modifier.weight(2f),
          contentAlignment = Alignment.TopCenter
        ) {
          Display()
        }
      }
    }
  }
}