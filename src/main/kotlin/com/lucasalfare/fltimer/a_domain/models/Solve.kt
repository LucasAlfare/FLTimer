package com.lucasalfare.fltimer.a_domain.models

data class Solve(
  var id: Int = -1,
  var time: Long,
  var scramble: String,
  var penalty: Penalty,
  var comment: String
)