import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun App() {
  Column {
    Scramble()
    Display()
    Solves()
  }
}