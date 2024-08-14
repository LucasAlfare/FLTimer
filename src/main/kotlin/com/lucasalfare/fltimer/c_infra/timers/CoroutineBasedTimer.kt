package com.lucasalfare.fltimer.c_infra.timers

import com.lucasalfare.fltimer.a_domain.AppDataState
import com.lucasalfare.fltimer.a_domain.Timer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoroutineBasedTimer(override var appDataState: AppDataState) : Timer {

  private var repeater: Job? = null
  private val auxScope = CoroutineScope(Job())

  override suspend fun startInspection() {
    println("${this.javaClass.simpleName} inspection is started.")
  }

  override suspend fun stopInspection() {
    println("${this.javaClass.simpleName} inspection is stoped.")
  }

  override suspend fun startTimer() {
    println("${this.javaClass.simpleName} timer is started.")

    repeater = asyncRoutine {
      appDataState.timerCurrentDisplayTime =
        System.currentTimeMillis() - appDataState.startMoment
    }
  }

  override suspend fun stopTimer() {
    println("${this.javaClass.simpleName} timer is stoped.")

    appDataState.timerCurrentDisplayTime =
      appDataState.stopMoment - appDataState.startMoment

    repeater!!.cancel()
  }

  private fun asyncRoutine(
    delayTime: Long = 1L,
    callback: () -> Unit
  ) = auxScope.launch {
    while (true) {
      callback()
      delay(delayTime)
    }
  }
}