@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package lucasalfare.fltimer.desktop

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import lucasalfare.fltimer.core.*
import lucasalfare.fltimer.core.configuration.Config
import lucasalfare.fltimer.core.configuration.ConfigurationManager
import lucasalfare.fltimer.core.data.SolvesManager
import lucasalfare.fltimer.core.data.session.SessionManager
import lucasalfare.fltimer.core.networking.NetworkManager
import lucasalfare.fltimer.core.scramble.ScrambleManager
import lucasalfare.fltimer.core.timer.TimerManager
import lucasalfare.fltimer.ui.composables.Display
import lucasalfare.fltimer.ui.uiManager

@Composable
fun UserTimes(userId: String, times: LongArray) {
  Box(modifier = Modifier.fillMaxHeight().width(100.dp).border(5.dp, Color.Gray).padding(12.dp)) {
    Column {
      Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
        Text(userId)
      }

      LazyColumn {
        times.forEachIndexed { index, l ->
          item {
            Text("${index + 1}. ${l.toTimestamp()}")
            Divider()
          }
        }
      }
    }
  }
}