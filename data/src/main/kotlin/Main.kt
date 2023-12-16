import crud.createSolve
import crud.getSolves

fun main() {
  configureDatabase()
  createSolve(10, "R2 D2 U' L F2", null, "DNF")
  val solves = getSolves()
  solves.forEach { println(it) }
}