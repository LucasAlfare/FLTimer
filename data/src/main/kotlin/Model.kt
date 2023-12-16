// Entidades de domínio
data class Solve(
  val id: Int,
  val time: Long,
  val scramble: String,
  val comment: String?,
  val penalty: String
)

data class Session(
  val id: Int,
  val name: String
)