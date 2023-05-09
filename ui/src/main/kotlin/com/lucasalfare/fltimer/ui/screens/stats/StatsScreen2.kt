package com.lucasalfare.fltimer.ui.screens.stats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.lucasalfare.fltimer.core.model.FLTimerState
import com.lucasalfare.fltimer.core.statistics.best
import com.lucasalfare.fltimer.core.statistics.worst
import com.lucasalfare.fltimer.core.toTimestamp

@Composable
fun StatsScreen2() {
  val solves = FLTimerState.getCurrentActiveSession().solves

  SelectionContainer {
    LazyColumn {
      item {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("\n")
          Text("best:")
          TextButton(onClick = {}) { Text(solves.best().result.toTimestamp()) }
        }
      }

      item {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("\n")
          Text("worst:")
          TextButton(onClick = {}) { Text(solves.worst().result.toTimestamp()) }
        }
      }

      item {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("\n")
          Text("best:")
          TextButton(onClick = {}) { Text(solves.best().result.toTimestamp()) }
        }
      }

      item {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("\n")
          Text("worst:")
          TextButton(onClick = {}) { Text(solves.worst().result.toTimestamp()) }
        }
      }
      item {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("\n")
          Text("best:")
          TextButton(onClick = {}) { Text(solves.best().result.toTimestamp()) }
        }
      }

      item {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("\n")
          Text("worst:")
          TextButton(onClick = {}) { Text(solves.worst().result.toTimestamp()) }
        }
      }
      item {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("\n")
          Text("best:")
          TextButton(onClick = {}) { Text(solves.best().result.toTimestamp()) }
        }
      }

      item {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("\n")
          Text("worst:")
          TextButton(onClick = {}) { Text(solves.worst().result.toTimestamp()) }
        }
      }
      item {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("\n")
          Text("best:")
          TextButton(onClick = {}) { Text(solves.best().result.toTimestamp()) }
        }
      }

      item {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("\n")
          Text("worst:")
          TextButton(onClick = {}) { Text(solves.worst().result.toTimestamp()) }
        }
      }
      item {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("\n")
          Text("best:")
          TextButton(onClick = {}) { Text(solves.best().result.toTimestamp()) }
        }
      }

      item {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("\n")
          Text("worst:")
          TextButton(onClick = {}) { Text(solves.worst().result.toTimestamp()) }
        }
      }
      item {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("\n")
          Text("best:")
          TextButton(onClick = {}) { Text(solves.best().result.toTimestamp()) }
        }
      }

      item {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("\n")
          Text("worst:")
          TextButton(onClick = {}) { Text(solves.worst().result.toTimestamp()) }
        }
      }
      item {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("\n")
          Text("best:")
          TextButton(onClick = {}) { Text(solves.best().result.toTimestamp()) }
        }
      }

      item {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("\n")
          Text("worst:")
          TextButton(onClick = {}) { Text(solves.worst().result.toTimestamp()) }
        }
      }
      item {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("\n")
          Text("best:")
          TextButton(onClick = {}) { Text(solves.best().result.toTimestamp()) }
        }
      }

      item {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("\n")
          Text("worst:")
          TextButton(onClick = {}) { Text(solves.worst().result.toTimestamp()) }
        }
      }
    }
  }
}
