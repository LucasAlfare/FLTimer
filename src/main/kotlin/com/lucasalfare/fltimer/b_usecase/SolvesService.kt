package com.lucasalfare.fltimer.b_usecase

import com.lucasalfare.fltimer.a_domain.AppDataState
import com.lucasalfare.fltimer.a_domain.Listener
import com.lucasalfare.fltimer.a_domain.repository.SolvesRepository

class SolvesService(
  private var solvesRepository: SolvesRepository,
  private var appState: AppDataState
) : Listener {

  override suspend fun onEvent(event: String, data: Any?) {
    if (event == "timer-finish") {
      createSolve()
      appState.allSolves = getAllSolves()
    }
  }

  // we create solves based on what we have in the state
  private suspend fun createSolve() {
    solvesRepository.create(
      time = appState.stopMoment - appState.startMoment,
      scramble = appState.scramble,
      penalty = appState.penalty,
      comment = appState.comment
    )
  }

  private suspend fun getAllSolves() = solvesRepository.getAll()
}