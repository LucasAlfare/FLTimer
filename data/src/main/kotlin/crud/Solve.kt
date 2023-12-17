package crud

import Penalty
import Solve
import sqlExecute
import sqlExecuteQuery

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

fun deleteSolveById(solveId: Int) {
  sqlExecuteQuery(
    """
    DELETE FROM Solves WHERE id = $solveId;
    """.trimIndent()
  )
}