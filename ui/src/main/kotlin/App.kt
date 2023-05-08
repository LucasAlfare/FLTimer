import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lucasalfare.fltimer.ui.FLTimerUiState.Companion.currentTabName
import com.lucasalfare.fltimer.ui.TabName
import com.lucasalfare.fltimer.ui.Tabs
import com.lucasalfare.fltimer.ui.screens.details.Details
import com.lucasalfare.fltimer.ui.screens.solves.Solves
import com.lucasalfare.fltimer.ui.screens.stats.StatsScreen
import com.lucasalfare.fltimer.ui.screens.timer.Timer


@Composable
fun App() {
  Column(modifier = Modifier.fillMaxSize()) {
    Box(modifier = Modifier.weight(1f)) {
      when (currentTabName.value) {
        TabName.Timer.name -> Timer()
        TabName.Solves.name -> Solves()
        TabName.Stats.name -> StatsScreen()
        TabName.Details.name -> Details()
        else -> Text(currentTabName.value)
      }
    }

    Box(modifier = Modifier.weight(1f / 6)) {
      Tabs()
    }
  }
}