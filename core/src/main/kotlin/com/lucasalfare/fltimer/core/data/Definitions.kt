package com.lucasalfare.fltimer.core.data

import com.lucasalfare.fltimer.core.scramble.Category
import com.lucasalfare.fltimer.core.toTimestamp
import java.util.*


const val DefaultSessionName = "Default"

enum class Penalty(val code: Int) {
  /**
   * Flags a time as valid.
   */
  Ok(0),

  /**
   * Flags a time to be sum with 2_000 milliseconds in statistics.
   */
  PlusTwo(1),

  /**
   * Flags a time as not finished.
   */
  Dnf(2);

  companion object {
    fun getPenaltyByCode(code: Int) = when (code) {
      1 -> Penalty.PlusTwo; 2 -> Penalty.Dnf; else -> Penalty.Ok
    }
  }
}

/**
 * Main elementar piece of data of the application.
 */
data class Solve(
  var time: Long = 0L,
  var scramble: String = "[no scramble]", // TODO: introduce Scramble Object type
  var penalty: Penalty = Penalty.Ok,
  var comment: String = "",
  var id: UUID = UUID.randomUUID()
) {
  fun getDisplayableRepresentation() = when (penalty) {
    Penalty.PlusTwo -> "+${(time + 2000).toTimestamp()}"
    Penalty.Dnf -> "DNF"
    else -> time.toTimestamp()
  }

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

  fun nDNFs() = this.filter { it.value.penalty == Penalty.Dnf }.size

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
