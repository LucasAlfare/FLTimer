package com.lucasalfare.fltimer.data

import com.lucasalfare.fltimer.model.Penalty

object Solves {

  fun create(
    time: Long = 0L,
    scramble: String = "",
    penalty: Penalty = Penalty.Ok,
    comment: String = ""
  ) {
  }

  fun getBySessionName(sessionName: String) {}

  fun getByPenalty(penalty: Penalty) {}

  fun getByComment(comment: String) {}

  // Note: null value indicates that the current existing value should not be changed.
  // In other words, the specific value will only be updated if it is not null in the args.
  fun update(
    time: Long? = null,
    scramble: String? = null,
    penalty: Penalty? = null,
    comment: String? = null
  ) {
  }

  fun deleteById(id: Int) {}

  fun deleteByPenalty(penalty: Penalty) {}
}