import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.lucasalfare.fltimer.core.FLTimerState

@Composable
fun Solves() {
  FLTimerState.getCurrentActiveSession().solves.values.forEach { println(it) }
//  LazyColumn {
//    FLTimerState.getCurrentActiveSession().solves.values.forEach {
//      item {
//        Text("${it.getDisplayableRepresentation()}. ${it.scramble}")
//        Divider()
//      }
//    }
//  }
}