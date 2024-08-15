package com.lucasalfare.fltimer.c_infra.state.compose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.lucasalfare.fltimer.a_domain.AppDataState
import com.lucasalfare.fltimer.a_domain.TimerState
import com.lucasalfare.fltimer.a_domain.models.Penalty
import com.lucasalfare.fltimer.a_domain.models.Solve

// uses [MutableState<T>] types, that makes Compose automatically whatch to
object ComposeAppDataState : AppDataState {
  override var timerState: TimerState by mutableStateOf(TimerState.Ready)
  override var timerCurrentCountingTime: Long by mutableStateOf(0L)
  override var startInspectionMoment: Long by mutableStateOf(0L)
  override var stopInspectionMoment: Long by mutableStateOf(0L)
  override var startMoment: Long by mutableStateOf(0L)
  override var stopMoment: Long by mutableStateOf(0L)
  override var scramble: String by mutableStateOf("")
  override var penalty: Penalty by mutableStateOf(Penalty.Ok)
  override var comment: String by mutableStateOf("")

  private val _allSolves: SnapshotStateList<Solve> = mutableStateListOf()
  override var allSolves: List<Solve>
    get() = _allSolves
    set(value) {
      _allSolves.clear()
      _allSolves.addAll(value)
    }
}