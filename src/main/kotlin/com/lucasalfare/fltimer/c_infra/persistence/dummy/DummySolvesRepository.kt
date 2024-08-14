package com.lucasalfare.fltimer.c_infra.persistence.dummy

import com.lucasalfare.fltimer.a_domain.models.Penalty
import com.lucasalfare.fltimer.a_domain.models.Solve
import com.lucasalfare.fltimer.a_domain.repository.SolvesRepository
import kotlin.math.abs
import kotlin.random.Random

object DummySolvesRepository : SolvesRepository {

  // array is better to look-up
  private val data = mutableListOf<Solve>()

  override suspend fun create(time: Long, scramble: String, penalty: Penalty, comment: String): Int {
    val nextId = abs(Random.nextInt())
    if (data.any { it.id == nextId }) return -1
    val nextSolve = Solve(
      id = nextId,
      time = time,
      scramble = scramble,
      penalty = penalty,
      comment = comment
    )
    data += nextSolve
    return nextId
  }

  override suspend fun get(id: Int): Solve? {
    return data.singleOrNull { it.id == id }
  }

  override suspend fun getAll(): List<Solve> {
    return data
  }

  override suspend fun update(
    id: Int,
    nextTime: Long?,
    nextScramble: String?,
    nextPenalty: Penalty?,
    nextComment: String?
  ): Boolean {
    return data.singleOrNull { it.id == id }.let {
      if (it == null) {
        false
      } else {
        it.time = nextTime ?: it.time
        it.scramble = nextScramble ?: it.scramble
        it.penalty = nextPenalty ?: it.penalty
        it.comment = nextComment ?: it.comment
        true
      }
    }
  }

  override suspend fun delete(id: Int): Boolean {
    return data.removeIf { it.id == id }
  }
}