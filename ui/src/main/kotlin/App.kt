import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.lucasalfare.fltimer.ui.Display
import com.lucasalfare.fltimer.ui.Scramble
import com.lucasalfare.fltimer.ui.Solves

@Composable
fun App() {
  Column {
    Scramble()
    Display()
    Solves()
  }
}