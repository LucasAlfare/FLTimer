package crud

import Penalty
import Solve
import sqlExecute
import sqlExecuteQuery

/**
 * Initializes the 'Solves' table in the database if it doesn't already exist.
 * The table includes columns for solve information such as time, scramble, penalty, comment,
 * and a foreign key referencing the 'Sessions' table.
 */
fun initSolvesTable() {
  sqlExecute(
    """
    CREATE TABLE IF NOT EXISTS Solves (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      time INTEGER,
      scramble TEXT,
      penalty TEXT CHECK penalty IN ('ok', 'plus_two', 'dnf'),
      comment TEXT,
      session_id INT,
      FOREIGN KEY (session_id) REFERENCES Sessions(id),
      CONSTRAINT unique_id UNIQUE (id)
    );
    """.trimIndent()
  )
}

/**
 * Creates a new solve record in the 'Solves' table with the provided information.
 *
 * @param time The time taken for the solve in milliseconds.
 * @param scramble The scramble notation for the solve.
 * @param penalty The penalty for the solve (ok, plus_two, dnf).
 * @param comment Additional comments or notes about the solve.
 * @param sessionId The ID of the session to which the solve belongs.
 */
fun createSolve(
  time: Long,
  scramble: String,
  penalty: Penalty,
  comment: String,
  sessionId: Int
) {
  sqlExecute(
    """
    INSERT INTO Solves (time, scramble, penalty, comment, session_id) VALUES (
      $time,
      $scramble,
      ${penalty.name},
      $comment,
      $sessionId
    );
    """.trimIndent()
  )
}

/**
 * Retrieves a solve from the 'Solves' table based on its ID.
 *
 * @param solveId The ID of the solve to retrieve.
 * @return The Solve object if found, or null if not found.
 */
fun getSolveById(solveId: Int): Solve? {
  val resultSet = sqlExecuteQuery(
    """
    "SELECT * FROM Solves WHERE id = $solveId"
    """.trimIndent()
  )

  if (resultSet != null) {
    val search: Solve
    while (resultSet.next()) {
      search = Solve(
        id = resultSet.getInt("id"),
        time = resultSet.getLong("time"),
        scramble = resultSet.getString("scramble"),
        penalty = Penalty.valueOf(resultSet.getString("penalty")),
        comment = resultSet.getString("comment")
      )
      resultSet.close()
      return search
    }
    resultSet.close()
  }

  return null
}

/**
 * Updates the information of a solve in the 'Solves' table based on its ID.
 *
 * @param solveId The ID of the solve to update.
 * @param updatedSolve The updated Solve object containing new information.
 */
fun updateSolve(solveId: Int, updatedSolve: Solve) {
  sqlExecute(
    """
    UPDATE Solves 
    SET 
      time = ${updatedSolve.time},
      scramble = '${updatedSolve.scramble}',
      penalty = '${updatedSolve.penalty.name}',
      comment = '${updatedSolve.comment}'
    WHERE id = $solveId;
    """.trimIndent()
  )
}

/**
 * Deletes a solve from the 'Solves' table based on its ID.
 *
 * @param solveId The ID of the solve to delete.
 */
fun deleteSolveById(solveId: Int) {
  sqlExecuteQuery(
    """
    DELETE FROM Solves WHERE id = $solveId;
    """.trimIndent()
  )
}