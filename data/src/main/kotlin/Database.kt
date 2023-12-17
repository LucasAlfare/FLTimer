import java.sql.DriverManager
import java.sql.ResultSet

private const val databaseUrl = "jdbc:sqlite:fltimer_data.db"
private val connection = DriverManager.getConnection(databaseUrl)

fun sqlExecute(definitions: String) {
  connection.use { connection ->
    connection.createStatement().use { statement ->
      statement.execute(definitions)
    }
  }
}

fun sqlExecuteQuery(query: String): ResultSet? {
  val statement = connection.createStatement()
  val result = statement.executeQuery(query)
  return result
}
