package com.lucasalfare.fltimer

import com.lucasalfare.fltimer.b_usecase.SolvesService
import com.lucasalfare.fltimer.b_usecase.TimerService
import com.lucasalfare.fltimer.c_infra.persistence.dummy.DummySolvesRepository
import com.lucasalfare.fltimer.c_infra.state.compose.ComposeAppDataState
import com.lucasalfare.fltimer.c_infra.timers.CoroutineBasedTimer
import com.lucasalfare.fltimer.c_infra.ui.compose.ComposeWindowLauncher


fun main() {
  val currentUsedAppState = ComposeAppDataState

  val currentTimer = CoroutineBasedTimer(currentUsedAppState)
  val solvesService = SolvesService(DummySolvesRepository, currentUsedAppState)
  val timerService = TimerService(currentTimer)
  val screenLauncher = ComposeWindowLauncher(timerService)

  timerService.addListener(solvesService)
  screenLauncher.launch()
}