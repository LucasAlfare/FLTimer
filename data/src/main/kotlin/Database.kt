import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

/**
 * URL for connecting to the SQLite database.
 */
private const val databaseUrl = "jdbc:sqlite:fltimer_data.db"

/**
 * Initial connection to the database using the specified URL.
 */
private val connection = DriverManager.getConnection(databaseUrl)

/**
 * Executes SQL statements for update, insert, or delete operations in the database.
 *
 * @param definitions SQL statements to be executed as a String.
 * @param targetConnection Specific connection to which the SQL statements will be applied
 *                         (default: initial connection).
 */
fun sqlExecute(definitions: String, targetConnection: Connection = connection) {
  targetConnection.createStatement().use { statement ->
    statement.execute(definitions)
  }
}

/**
 * Executes an SQL query in the database.
 *
 * @param query SQL query to be executed as a String.
 * @param targetConnection Specific connection in which the query will be performed
 *                         (default: initial connection).
 * @return ResultSet containing the results of the query or null if an error occurs.
 */
fun sqlExecuteQuery(query: String, targetConnection: Connection = connection): ResultSet? {
  val statement = targetConnection.createStatement()
  val result = statement.executeQuery(query)
  return result
}
