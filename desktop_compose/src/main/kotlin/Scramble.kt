import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp
import com.lucasalfare.fltimer.core.model.FLTimerModel

@Composable
fun Scramble() {
  val scramble = remember { FLTimerModel.currentScramble }

  Text(text = scramble.value, fontSize = 16.sp)
}