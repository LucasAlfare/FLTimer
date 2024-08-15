package com.lucasalfare.fltimer

import com.lucasalfare.fltimer.a_domain.AppDataState
import com.lucasalfare.fltimer.a_domain.Timer
import com.lucasalfare.fltimer.a_domain.TimerState
import com.lucasalfare.fltimer.a_domain.models.Penalty
import com.lucasalfare.fltimer.b_usecase.SolvesService
import com.lucasalfare.fltimer.b_usecase.TimerService
import com.lucasalfare.fltimer.c_infra.persistence.dummy.DummySolvesRepository
import com.lucasalfare.fltimer.c_infra.timers.CoroutineBasedTimer
import com.lucasalfare.fltimer.c_infra.ui.swing.MySwingScreenLauncher

object DummyState : AppDataState {
  private var _timerState: TimerState = TimerState.Ready
  private var _timerCurrentCountingTime: Long = 0L
  private var _startInspectionMoment: Long = 0L
  private var _stopInspectionMoment: Long = 0L
  private var _startMoment: Long = 0L
  private var _stopMoment: Long = 0L
  private var _scramble: String = ""
  private var _penalty: Penalty = Penalty.Ok
  private var _comment: String = ""

  override var timerState: TimerState
    get() = _timerState
    set(value) {
      _timerState = value
    }

  override var timerCurrentCountingTime: Long
    get() = _timerCurrentCountingTime
    set(value) {
      _timerCurrentCountingTime = value
    }

  override var startInspectionMoment: Long
    get() = _startInspectionMoment
    set(value) {
      _startInspectionMoment = value
    }

  override var stopInspectionMoment: Long
    get() = _stopInspectionMoment
    set(value) {
      _stopInspectionMoment = value
    }

  override var startMoment: Long
    get() = _startMoment
    set(value) {
      _startMoment = value
    }

  override var stopMoment: Long
    get() = _stopMoment
    set(value) {
      _stopMoment = value
    }

  override var scramble: String
    get() = _scramble
    set(value) {
      _scramble = value
    }

  override var penalty: Penalty
    get() = _penalty
    set(value) {
      _penalty = value
    }

  override var comment: String
    get() = _comment
    set(value) {
      _comment = value
    }
}

class DummyTimer(override var appDataState: AppDataState) : Timer {
  override suspend fun startInspection() {
    println("${this.javaClass.simpleName} inspection is started.")
  }

  override suspend fun stopInspection() {
    println("${this.javaClass.simpleName} inspection is stoped.")
  }

  override suspend fun startTimer() {
    println("${this.javaClass.simpleName} timer is started.")
  }

  override suspend fun stopTimer() {
    println("${this.javaClass.simpleName} timer is stoped.")
  }
}

fun main() {
  val currentUsedAppState = DummyState
  val currentTimer = CoroutineBasedTimer(currentUsedAppState)
  val solvesService = SolvesService(DummySolvesRepository, currentUsedAppState)
  val timerService = TimerService(currentTimer)
  val screen = MySwingScreenLauncher(timerService)

  timerService.addListener(solvesService)
  screen.launch()
}