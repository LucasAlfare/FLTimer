package com.lucasalfare.fltimer.model

class Preferences {
  companion object {
    enum class UiTheme {
      Light, Dark
    }
  }
}

enum class PuzzleCategory {
  RubiksCube,
  PocketCube
}

enum class Penalty {
  Ok, PlusTwo, Dnf
}

data class Solve(
  val id: Long,
  val time: Long,
  val scramble: String,
  val penalty: Penalty,
  val comment: String
)

data class Session(
  val id: Long,
  val name: String,
  val puzzleCategory: PuzzleCategory
)