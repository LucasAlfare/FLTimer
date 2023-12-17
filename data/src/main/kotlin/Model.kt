enum class Penalty {
  ok, plus_two, dnf
}

data class Solve(
  val id: Int,
  val time: Long,
  val scramble: String,
  val penalty: Penalty,
  val comment: String
)

data class Session(
  val id: Int,
  val name: String
)