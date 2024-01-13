package com.lucasalfare.fltimer.scramble

enum class Category(val code: Int) {
  RubiksCube(0),
  RubiksCubeOneHanded(1),
  RubiksCubeOneBlindfolded(2),
  PocketCube(3);

  companion object {
    fun getCategoryByCode(code: Int) = entries.first { it.code == code }
  }
}