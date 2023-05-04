package com.lucasalfare.fltimer.ui.screens.solves

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Solves() {
  Box(modifier = Modifier.fillMaxSize()) {
    Column {
      Box(
        Modifier
          .fillMaxWidth()
      ) {
        SessionsController()
      }

      Box(
        Modifier
      ) {
        TimesList()
      }
    }
  }
}