import crud.createSolve
import crud.getSolves

fun main() {
  configureDatabase()

  // Agora você pode realizar operações no banco de dados usando o Exposed
  createSolve(10, "R2 D2 U' L F2", null, "DNF")
  val solves = getSolves()
  solves.forEach { println(it) }
}