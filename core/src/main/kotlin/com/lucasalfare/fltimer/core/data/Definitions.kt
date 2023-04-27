package com.lucasalfare.fltimer.core.data


const val DefaultSessionName = "Default"

enum class Penalty(val code: Int) {
  /**
   * Flags a time as valid.
   */
  Ok(0),

  /**
   * Flags a time to be sum with 2_000 milliseconds in statistics.
   */
  PlusTwo(1),

  /**
   * Flags a time as not finished.
   */
  Dnf(2);

  companion object {
    fun getPenaltyByCode(code: Int) = when (code) {
      1 -> PlusTwo; 2 -> Dnf; else -> Ok
    }
  }
}
