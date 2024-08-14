package com.lucasalfare.fltimer.a_domain.repository

import com.lucasalfare.fltimer.a_domain.models.Penalty
import com.lucasalfare.fltimer.a_domain.models.Solve

interface SolvesRepository {

  suspend fun create(time: Long, scramble: String, penalty: Penalty, comment: String): Int

  suspend fun get(id: Int): Solve?

  suspend fun getAll(): List<Solve>

  suspend fun update(
    id: Int,
    nextTime: Long? = null,
    nextScramble: String? = null,
    nextPenalty: Penalty? = null,
    nextComment: String? = null
  ): Boolean

  suspend fun delete(id: Int): Boolean
}