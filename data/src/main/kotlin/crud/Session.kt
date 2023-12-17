package crud

import Penalty
import Session
import Solve
import sqlExecute
import sqlExecuteQuery

/**
 * Initializes the 'Sessions' table in the database if it doesn't already exist.
 * The table includes columns for session information such as ID and name.
 */
fun initSessionsTable() {
  sqlExecute(
    """
    CREATE TABLE IF NOT EXISTS Sessions (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT,
        CONSTRAINT unique_id UNIQUE (id)
    );
    """.trimIndent()
  )
}

/**
 * Creates a new session record in the 'Sessions' table with the provided name.
 *
 * @param name The name of the new session.
 */
fun createSession(name: String) {
  sqlExecute(
    """
    INSERT INTO Sessions (name) VALUES ('$name');
    """.trimIndent()
  )
}

/**
 * Retrieves a session from the 'Sessions' table based on its ID.
 *
 * @param sessionId The ID of the session to retrieve.
 * @return The Session object if found, or null if not found.
 */
fun getSessionById(sessionId: Int): Session? {
  val resultSet = sqlExecuteQuery(
    """
    "SELECT * FROM Sessions WHERE id = $sessionId"
    """.trimIndent()
  )

  if (resultSet != null) {
    val search: Session
    while (resultSet.next()) {
      search = Session(
        id = resultSet.getInt("id"),
        name = resultSet.getString("name")
      )
      resultSet.close()
      return search
    }
    resultSet.close()
  }

  return null
}

/**
 * Retrieves a list of all session names from the 'Sessions' table.
 *
 * @return A list of session names.
 */
fun getAllSessionNames(): List<String> {
  val sessionNames = mutableListOf<String>()

  sqlExecuteQuery("SELECT name FROM Sessions")?.use { resultSet ->
    while (resultSet.next()) {
      sessionNames.add(resultSet.getString("name"))
    }
    resultSet.close()
  }

  return sessionNames
}

/**
 * Retrieves a list of solves associated with a specific session name.
 *
 * @param sessionName The name of the session to retrieve solves for.
 * @return A list of Solve objects for the specified session, or null if not found.
 */
fun getAllSolvesBySessionName(sessionName: String): MutableList<Solve>? {
  val resultSet = sqlExecuteQuery(
    """
    SELECT Solves.*
    FROM Solves
    JOIN Sessions ON Solves.session_id = Sessions.id
    WHERE Sessions.name = '$sessionName';
    """.trimIndent()
  )

  if (resultSet != null) {
    val solvesList = mutableListOf<Solve>()
    while (resultSet.next()) {
      solvesList += Solve(
        id = resultSet.getInt("id"),
        time = resultSet.getLong("time"),
        scramble = resultSet.getString("scramble"),
        penalty = Penalty.valueOf(resultSet.getString("penalty")),
        comment = resultSet.getString("comment")
      )
    }
    resultSet.close()
    return solvesList
  }
  return null
}

/**
 * Updates the name of a session in the 'Sessions' table based on its ID.
 *
 * @param sessionId The ID of the session to update.
 * @param updatedName The updated name for the session.
 */
fun updateSession(sessionId: Int, updatedName: String) {
  sqlExecute(
    """
    UPDATE Sessions SET name = '$updatedName' WHERE id = $sessionId;
    """.trimIndent()
  )
}

/**
 * Deletes a session and all associated solves from the 'Sessions' and 'Solves' tables based on its ID.
 *
 * @param sessionId The ID of the session to delete.
 */
fun deleteSessionById(sessionId: Int) {
  sqlExecute(
    """
    DELETE FROM Solves WHERE session_id = $sessionId;
    """.trimIndent()
  )

  sqlExecute(
    """
    DELETE FROM Sessions WHERE id = $sessionId;
    """.trimIndent()
  )
}