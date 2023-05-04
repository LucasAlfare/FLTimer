package com.lucasalfare.fltimer.ui.screens.timer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Timer() {
  Box(modifier = Modifier.fillMaxSize()) {
    Box(modifier = Modifier.align(Alignment.TopCenter)) {
      Scramble()
    }

    Box(modifier = Modifier.align(Alignment.Center)) {
      Box {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Display()
          ToggleInspection()
        }
      }
    }
  }
}