package com.lucasalfare.fltimer.data

import com.lucasalfare.fltimer.model.Penalty
import com.lucasalfare.fltimer.model.Solve
import kotlin.random.Random

private val tmpSolves = mutableListOf<Solve>()

object Solves {

  fun create(
    time: Long = 0L,
    scramble: String = "",
    penalty: Penalty = Penalty.Ok,
    comment: String = ""
  ) {
    val nextSolve = Solve(
      id = Random.nextLong(),
      time = time,
      scramble = scramble,
      penalty = penalty,
      comment = comment
    )

    tmpSolves += nextSolve
  }

  fun getAll() = tmpSolves

  fun getById(id: Long) = tmpSolves.find { it.id == id }

  fun update(
    targetId: Long,
    time: Long? = 0L,
    scramble: String? = "",
    penalty: Penalty? = Penalty.Ok,
    comment: String? = ""
  ) {
    tmpSolves.find { it.id == targetId }?.let {
      it.time = time ?: it.time
      it.scramble = scramble ?: it.scramble
      it.penalty = penalty ?: it.penalty
      it.comment = comment ?: it.comment
    }
  }

  fun deleteById(id: Long) {
    tmpSolves.removeIf { it.id == id }
  }
}