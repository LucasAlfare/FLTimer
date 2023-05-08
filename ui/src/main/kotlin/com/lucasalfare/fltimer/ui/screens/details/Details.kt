package com.lucasalfare.fltimer.ui.screens.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lucasalfare.fltimer.ui.screens.solves.Solves
import com.lucasalfare.fltimer.ui.screens.stats.StatsScreen

@Composable
fun Details() {
  Row {
    Box(Modifier.weight(1f)) {
      Solves()
    }

    Box(Modifier.weight(1f)) {
      StatsScreen()
    }
  }
}