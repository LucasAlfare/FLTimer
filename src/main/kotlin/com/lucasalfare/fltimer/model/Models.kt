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