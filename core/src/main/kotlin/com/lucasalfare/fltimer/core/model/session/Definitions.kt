package com.lucasalfare.fltimer.core.model.session

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.lucasalfare.fltimer.core.model.Solve
import com.lucasalfare.fltimer.core.scramble.Category

class Session(
  name: String = "",
  category: Category = Category.RubiksCube
) {
  var name by mutableStateOf(name)
  var category by mutableStateOf(category)
  val solves = mutableStateListOf<Solve>()
}