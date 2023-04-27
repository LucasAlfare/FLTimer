package com.lucasalfare.fltimer.core.modeling

import androidx.compose.runtime.*
import com.lucasalfare.fltimer.core.configuration.Config
import com.lucasalfare.fltimer.core.data.Penalty
import java.util.UUID

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
  name: String = ""
) {
  var name by mutableStateOf(name)
  val solves = mutableStateListOf<Solve>()
}

class FLTimerModel {
  companion object {
    const val DEFAULT_SESSION_NAME = "Default"

    val sessions = mutableStateListOf(
      Session(name = DEFAULT_SESSION_NAME)
    )

    var currentActiveSessionName by mutableStateOf(DEFAULT_SESSION_NAME)
    val configurations = mutableStateMapOf<Config, Any>()

    fun getCurrentActiveSession() = sessions.first { it.name == currentActiveSessionName }
  }
}

