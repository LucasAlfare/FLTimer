package com.lucasalfare.fltimer.scramble.scramblers

import kotlin.random.Random

fun getFreeRubiksCubeScramble(): String {
  val moves = arrayOf("Rx", "Uy", "Fz", "Lx", "Dy", "Bz")
  val dirs = arrayOf(" ", "' ", "2 ")

  fun sameAxis(moveA: String, moveB: String, moveC: String): Boolean {
    val concat = "${moveA[1]}${moveB[1]}${moveC[1]}"
    return concat == "xxx" || concat == "yyy" || concat == "zzz"
  }

  var moveA = "  "
  var moveB = "  "
  var moveC: String

  var scramble = ""

  repeat(25) {
    while (true) {
      moveC = moves[Random.nextInt(moves.size)]
      if ((!sameAxis(moveA, moveB, moveC)) && (moveC != moveB)) {
        break
      }
    }

    moveA = moveB
    moveB = moveC
    scramble += "${moveC[0]}${dirs[Random.nextInt(dirs.size)]}"
  }

  return scramble
}