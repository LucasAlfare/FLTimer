package com.lucasalfare.fltimer.a_domain.models

data class Session(
  val id: Int = -1,
  val name: String,
  val solves: List<Solve>
)