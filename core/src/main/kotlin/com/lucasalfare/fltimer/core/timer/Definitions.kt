package com.lucasalfare.fltimer.core.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val AuxCoroutineScope = CoroutineScope(Job())
fun asyncRoutine(
  delayTime: Long = 1L,
  callback: () -> Unit
) = AuxCoroutineScope.launch {
  while (true) {
    callback()
    delay(delayTime)
  }
}
