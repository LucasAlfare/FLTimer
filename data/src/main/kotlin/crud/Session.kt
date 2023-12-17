package crud

import Penalty
import Session
import Solve
import sqlExecute
import sqlExecuteQuery

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

fun createSession(name: String) {
  sqlExecute(
    """
    INSERT INTO Sessions (name) VALUES ('$name');
    """.trimIndent()
  )
}

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

fun updateSession(sessionId: Int, updatedName: String) {
  sqlExecute(
    """
    UPDATE Sessions SET name = '$updatedName' WHERE id = $sessionId;
    """.trimIndent()
  )
}

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