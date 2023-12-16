import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

fun configureDatabase() {
  Database.connect(url = "jdbc:sqlite:fltimer_data.db", driver = "org.sqlite.JDBC")
  TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
  transaction {
    SchemaUtils.createMissingTablesAndColumns(SolveTable, SessionTable, SolveSessionTable)
  }
}

object SolveTable : IntIdTable() {
  val time = long("time")
  val scramble = text("scramble")
  val comment = text("comment").nullable()
  val penalty = text("penalty")
}

object SessionTable : IntIdTable() {
  val name = text("name")
}

object SolveSessionTable : IntIdTable() {
  val solveId = integer("solveId").references(SolveTable.id)
  val sessionId = integer("sessionId").references(SessionTable.id)
}