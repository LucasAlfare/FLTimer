import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.modeling.FLTimerModel

@Composable
fun Solves() {
  val solves = remember { FLTimerModel.getCurrentActiveSession().solves }

  LazyColumn {
    solves.forEach {
      item {
        Text("${it.time}.  ${it.scramble}")
        Divider()
      }
    }
  }
}