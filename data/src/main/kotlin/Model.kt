/**
 * Enum class representing different penalty types that can be applied to a solve.
 * - [Ok]: No penalty
 * - [PlusTwo]: Plus two seconds penalty
 * - [Dnf]: Did not finish penalty
 */
enum class Penalty {
  Ok, PlusTwo, Dnf
}

/**
 * Data class representing a solve in a cubing session.
 * @property id Unique identifier for the solve.
 * @property time Time taken to complete the solve in milliseconds.
 * @property scramble String representation of the scramble algorithm for the solve.
 * @property penalty The penalty applied to the solve, as defined by the [Penalty] enum.
 * @property comment Additional comments or notes about the solve.
 */
data class Solve(
  val id: Int,
  val time: Long,
  val scramble: String,
  val penalty: Penalty,
  val comment: String
)

/**
 * Data class representing a cubing session.
 * @property id Unique identifier for the session.
 * @property name Name or description of the session.
 */
data class Session(
  val id: Int,
  val name: String
)