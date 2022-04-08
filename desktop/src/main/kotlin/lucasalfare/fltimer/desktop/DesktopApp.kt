package lucasalfare.fltimer.desktop

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import lucasalfare.fltimer.ui.composables.Display
import lucasalfare.fltimer.ui.composables.Scramble
import lucasalfare.fltimer.ui.composables.SessionController
import lucasalfare.fltimer.ui.composables.TimesList

@Composable
fun DesktopApp() {
  Column {
    SessionController()
    Scramble()
    Display()
    TimesList()
  }
}