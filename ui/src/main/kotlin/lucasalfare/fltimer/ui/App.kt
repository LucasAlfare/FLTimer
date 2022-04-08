package lucasalfare.fltimer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import lucasalfare.fltimer.ui.composables.Display
import lucasalfare.fltimer.ui.composables.Scramble
import lucasalfare.fltimer.ui.composables.SessionController
import lucasalfare.fltimer.ui.composables.TimesList

@Composable
fun App() {
  Column {
    SessionController()
    Scramble()
    Display()
    TimesList()
  }
}