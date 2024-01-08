package com.lucasalfare.fltimer.data

import com.lucasalfare.fltimer.data.Solves.toSolve
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Singleton object handling operations related to sessions in the database.
 */
object Sessions {

  /**
   * Creates a new session with the given name and inserts it into the SessionsTable.
   * @param name The name or description of the new session.
   * @return The created session.
   */
  fun createSession(name: String): Session {
    return transaction {
      val id = SessionsTable.insertAndGetId {
        it[SessionsTable.name] = name
      }.value
      Session(id, name)
    }
  }

  /**
   * Retrieves a list of all sessions from the SessionsTable.
   * @return A list of all sessions.
   */
  fun getAllSessions(): List<Session> {
    return transaction {
      SessionsTable
        .selectAll()
        .map { it.toSession() }
    }
  }

  /**
   * Retrieves a list of all session names from the SessionsTable.
   * @return A list of all session names.
   */
  fun getAllSessionsNames(): List<String> {
    return transaction {
      SessionsTable.slice(SessionsTable.name).selectAll().map { it.toString() }
    }
  }

  /**
   * Retrieves a session with the specified ID from the SessionsTable.
   * @param id The ID of the session to retrieve.
   * @return The session with the specified ID, or null if not found.
   */
  fun getSessionById(id: Int): Session? {
    return transaction {
      SessionsTable
        .select { SessionsTable.id eq id }
        .singleOrNull()
        ?.toSession()
    }
  }

  /**
   * Retrieves a list of solves associated with the specified session name.
   * @param sessionName The name of the session to retrieve solves for.
   * @return A list of solves associated with the specified session name.
   */
  fun getSolvesForSession(sessionName: String): List<Solve> {
    return transaction {
      SolvesTable
        .innerJoin(SessionsTable, { SolvesTable.sessionId }, { SessionsTable.id })
        .slice(SolvesTable.columns)
        .select { SessionsTable.name eq sessionName }
        .map { it.toSolve() }
    }
  }

  /**
   * Updates the name of a session with the specified ID.
   * @param id The ID of the session to update.
   * @param newName The new name or description for the session.
   * @return The updated session, or null if the update was unsuccessful.
   */
  fun updateSessionName(id: Int, newName: String): Session? {
    return transaction {
      val updatedRows = SessionsTable
        .update({ SessionsTable.id eq id }) {
          it[name] = newName
        }
      if (updatedRows > 0) {
        SessionsTable.select { SessionsTable.id eq id }
          .singleOrNull()
          ?.toSession()
      } else {
        null
      }
    }
  }

  /**
   * Deletes a session with the specified ID from the SessionsTable.
   * @param id The ID of the session to delete.
   * @return True if the deletion was successful, false otherwise.
   */
  fun deleteSession(id: Int): Boolean {
    return transaction {
      SessionsTable
        .deleteWhere { SessionsTable.id eq id } > 0
    }
  }

  /**
   * Converts a database result row to a [Session] object.
   * @return The converted [Session] object.
   */
  private fun ResultRow.toSession(): Session {
    return Session(this[SessionsTable.id].value, this[SessionsTable.name])
  }
}
