import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lucasalfare.fltimer.ui.FLTimerUiState.Companion.currentTabName
import com.lucasalfare.fltimer.ui.TabName
import com.lucasalfare.fltimer.ui.Tabs
import com.lucasalfare.fltimer.ui.screens.solves.Solves
import com.lucasalfare.fltimer.ui.screens.timer.Timer

@Composable
fun App2() {
  Column(modifier = Modifier.fillMaxSize()) {
    Box(modifier = Modifier.weight(1f)) {
      when (currentTabName.value) {
        TabName.Timer.name -> {
          Timer()
        }

        TabName.Solves.name -> {
          Solves()
        }

        TabName.Stats.name -> {
          Text(currentTabName.value)
        }

        else -> {
          Text(currentTabName.value)
        }
      }
    }

    Box(modifier = Modifier.weight(1f / 6)) {
      Tabs()
    }
  }
}