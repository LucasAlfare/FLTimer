@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.lucasalfare.fltimer.desktop

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lucasalfare.fltimer.core.toTimestamp

@Composable
fun UserTimes(userId: String, times: LongArray) {
  Box(modifier = Modifier.fillMaxHeight().width(100.dp).border(5.dp, Color.Gray).padding(12.dp)) {
    Column {
      Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
        Text(userId)
      }

      LazyColumn {
        times.forEachIndexed { index, l ->
          item {
            Text("${index + 1}. ${l.toTimestamp()}")
            Divider()
          }
        }
      }
    }
  }
}