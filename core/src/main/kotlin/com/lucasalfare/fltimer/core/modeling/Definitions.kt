package com.lucasalfare.fltimer.core.modeling

import androidx.compose.runtime.*
import com.lucasalfare.fltimer.core.configuration.Config
import com.lucasalfare.fltimer.core.scramble.Category
import java.util.UUID


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


class Solve(
  val id: UUID = UUID.randomUUID(),
  time: Long = 0L,
  scramble: String = "",
  penalty: Penalty = Penalty.Ok,
  comment: String = ""
) {
  var time by mutableStateOf(time)
  var scramble by mutableStateOf(scramble)
  var penalty by mutableStateOf(penalty)
  var comment by mutableStateOf(comment)
}


class Session(
  name: String = "",
  category: Category = Category.RubiksCube
) {
  var name by mutableStateOf(name)
  var category by mutableStateOf(category)
  val solves = mutableStateListOf<Solve>()
}


@Suppress("MemberVisibilityCanBePrivate")
class FLTimerModel {
  companion object {
    private const val DEFAULT_SESSION_NAME = "Default"

    val sessions = mutableStateListOf(
      Session(name = DEFAULT_SESSION_NAME)
    )

    var currentActiveSessionName by mutableStateOf(DEFAULT_SESSION_NAME)
    val configurations = mutableStateMapOf<Config, Any>(
      Pair(Config.UseInspection, false),
      Pair(Config.ShowScramblesInDetailsUI, false),
      Pair(Config.NetworkingModeOn, false),
      Pair(Config.AskForTimerMode, false)
    )

    var currentScramble = mutableStateOf("loading...")
    var currentDisplayValue = mutableStateOf(0L)

    fun getCurrentActiveSession() = sessions.first { it.name == currentActiveSessionName }
  }
}