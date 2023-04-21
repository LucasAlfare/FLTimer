package com.lucasalfare.fltimer.ui.composables

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.lucasalfare.fltimer.core.AppEvent
import com.lucasalfare.fltimer.core.toTimestamp
import com.lucasalfare.fltimer.ui.uiManager

/*
TODO: implement dynamic text size based on application screen width size
 */
@Composable
fun Display() {
  var text by remember { mutableStateOf("ready") }
  var textSize by remember { mutableStateOf(65.sp) }

  DisposableEffect(true) {
    val callback = uiManager.addCallback { appEvent, data ->
      when (appEvent) {
        AppEvent.TimerUpdate -> {
          text = (data as Long).toTimestamp()
        }

        else -> {}
      }

      textSize = if (appEvent == AppEvent.TimerToggleDown) 45.sp else 65.sp
    }

    onDispose { uiManager.removeCallback(callback) }
  }

  Text(
    text = text,
    fontSize = textSize,
    fontFamily = FontFamily.Monospace,
    fontWeight = FontWeight.Bold
  )
}
