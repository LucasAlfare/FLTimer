package lucasalfare.fltimer.core.data

import java.util.*
import kotlin.collections.LinkedHashMap

/**
 * Enumeration holding the three possible penalties of a round.
 */
enum class Penalty {
  /**
   * Flags a time as valid.
   */
  Ok,

  /**
   * Flags a time to be sum with 2_000 milliseconds in statistics.
   */
  PlusTwo,

  /**
   * Flags a time as not finished.
   */
  Dnf
}

/**
 * Main piece of data of the application.
 */
data class Solve(
  var time: Long = 0L,
  var scramble: String = "[no scramble]",
  var penalty: Penalty = Penalty.Ok,
  var comment: String = "",
  val id: UUID = UUID.randomUUID()
) {
  fun copyOther(other: Solve) {
    this.time = other.time
    this.scramble = other.scramble
    this.penalty = other.penalty
    this.comment = other.comment
  }

  fun cloneSelf() = Solve(time, scramble, penalty, comment, id)
}

/**
 * Container to a [LinkedHashMap] with some shortcuts functions.
 */
class Solves(vararg someSolves: Solve) : LinkedHashMap<UUID, Solve>() {

  init {
    someSolves.forEach { this += it }
  }

  operator fun plusAssign(solve: Solve) {
    this[solve.id] = solve
  }

  operator fun minusAssign(solve: Solve) {
    this.remove(solve.id)
  }

  override fun clone(): Solves {
    val solves = Solves()
    keys.forEach { solves[it] = get(it)!! }
    return solves
  }

  override fun toString(): String {
    return this.values.toString()
  }
}

/**
 * Holds a [Solves] object in order to keep easy to track
 * different data groups.
 */
data class Session(var name: String, val solves: Solves = Solves())
