package com.lucasalfare.fltimer.core.scramble

fun getRubiksCubeMovesMap(): MutableMap<Int, String> {
  val moves = arrayOf("R", "U", "F", "L", "D", "B")
  val directions = arrayOf("", "'", "2")
  val map = mutableMapOf<Int, String>()
  var keyCount = 0
  moves.forEach { m ->
    directions.forEach { d ->
      map[keyCount] = "$m$d"
      keyCount++
    }
  }

  return map
}

enum class Category(val code: Int) {
  RubiksCube(0),
  RubiksCubeOneHanded(1),
  RubiksCubeOneBlindfolded(2),
  PocketCube(3);

  companion object {
    fun getCategoryByCode(code: Int) = Category
      .values()
      .first { it.code == code }
  }
}

