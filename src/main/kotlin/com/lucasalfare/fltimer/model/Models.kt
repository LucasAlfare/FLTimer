package com.lucasalfare.fltimer.model

enum class Penalty {
  Ok, PlusTwo, Dnf
}

data class Solve(
  val id: Long,
  var time: Long = 0L,
  var scramble: String = "",
  var penalty: Penalty = Penalty.Ok,
  var comment: String = ""
)