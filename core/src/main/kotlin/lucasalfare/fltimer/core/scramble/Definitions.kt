package lucasalfare.fltimer.core.scramble

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

enum class Category {
  RubiksCube
}

data class Scramble(var sequence: String, val category: Category)
