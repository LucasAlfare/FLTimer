package com.lucasalfare.fltimer.core.data.session

import com.lucasalfare.fltimer.core.data.Solves
import com.lucasalfare.fltimer.core.scramble.Category

/**
 * Holds a [Solves] object in order to keep easy to track
 * different data groups.
 */
data class Session(
  var name: String,
  var category: Category = Category.RubiksCube,
  var solves: Solves = Solves()
)
