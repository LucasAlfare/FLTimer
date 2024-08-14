package com.lucasalfare.fltimer.b_usecase

import com.lucasalfare.fltimer.a_domain.AppDataState
import com.lucasalfare.fltimer.a_domain.repository.SolvesRepository

class SolvesService(
  private var solvesRepository: SolvesRepository,
  private var appState: AppDataState
) {
  // we create solves based on what we have in the state
  suspend fun createSolve() {
    solvesRepository.create(
      time = appState.stopMoment - appState.startMoment,
      scramble = appState.scramble,
      penalty = appState.penalty,
      comment = appState.comment
    )
  }

  suspend fun getAllSolves() = solvesRepository.getAll()
}