package com.lucasalfare.fltimer.core.scramble.scramblers

import java.util.*

private val rand = Random()

fun getFreeRubiksCubeScramble(): String {
  val moves = arrayOf("Rx", "Uy", "Fz", "Lx", "Dy", "Bz")
  val dirs = arrayOf(" ", "' ", "2 ")

  fun sameAxis(moveA: String, moveB: String, moveC: String): Boolean {
    val concat = "${moveA[1]}${moveB[1]}${moveC[1]}"
    return concat == "xxx" || concat == "yyy" || concat == "zzz"
  }

  var moveA = "  "
  var moveB = "  "
  var moveC = "  "

  var scramble = ""

  repeat(25) {
    while (true) {
      moveC = moves[rand.nextInt(moves.size)]
      if ((!sameAxis(moveA, moveB, moveC)) && (moveC != moveB)) {
        break
      }
    }

    moveA = moveB
    moveB = moveC
    scramble += "${moveC[0]}${dirs[rand.nextInt(dirs.size)]}"
  }

  return scramble
}