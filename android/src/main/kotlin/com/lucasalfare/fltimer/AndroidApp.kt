package com.lucasalfare.fltimer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lucasalfare.fltimer.ui.Display
import com.lucasalfare.fltimer.ui.Scramble
import com.lucasalfare.fltimer.ui.TimesList

@Composable
fun AndroidApp() {
  Row {
//    Box(
//      modifier = Modifier
//        .padding(bottom = 8.dp)
//    ) {
//      Column {
//        TimesList()
//      }
//    }

    Box {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.weight(1f)) {
          Scramble()
        }

        Box(
          modifier = Modifier.weight(2f),
          contentAlignment = Alignment.TopCenter
        ) {
          Display()
        }
      }
    }
  }
}