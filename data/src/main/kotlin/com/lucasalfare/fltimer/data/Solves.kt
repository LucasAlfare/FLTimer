package com.lucasalfare.fltimer.data

import Penalty
import Solve
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Singleton object handling operations related to solves in the database.
 */
object Solves {

  /**
   * Creates a new solve and inserts it into the SolvesTable.
   * @param time The time taken to complete the solve in milliseconds.
   * @param scramble The scramble algorithm for the solve.
   * @param penalty The penalty applied to the solve, using the [Penalty] enum.
   * @param comment Additional comments or notes about the solve.
   * @param sessionId The ID of the session to which the solve belongs.
   * @return The created solve.
   */
  fun createSolve(
    time: Long,
    scramble: String,
    penalty: Penalty,
    comment: String,
    sessionId: Int
  ): Solve {
    return transaction {
      val id = SolvesTable
        .insertAndGetId {
          it[SolvesTable.time] = time
          it[SolvesTable.scramble] = scramble
          it[SolvesTable.penalty] = penalty
          it[SolvesTable.comment] = comment
          it[SolvesTable.sessionId] = sessionId
        }
        .value
      Solve(id, time, scramble, penalty, comment)
    }
  }

  /**
   * Retrieves a solve with the specified ID from the SolvesTable.
   * @param id The ID of the solve to retrieve.
   * @return The solve with the specified ID, or null if not found.
   */
  fun getSolveById(id: Long): Solve? {
    return transaction {
      SolvesTable
        .select { SolvesTable.id eq id }
        .singleOrNull()
        ?.toSolve()
    }
  }

  /**
   * Retrieves a list of all solves from the SolvesTable.
   * @return A list of all solves.
   */
  fun getAllSolves(): List<Solve> {
    return transaction {
      SolvesTable
        .selectAll()
        .map { it.toSolve() }
    }
  }

  /**
   * Updates the comment of a solve with the specified ID.
   * @param id The ID of the solve to update.
   * @param newComment The new comment or notes for the solve.
   * @return The updated solve, or null if the update was unsuccessful.
   */
  fun updateSolveComment(id: Long, newComment: String): Solve? {
    return transaction {
      val updatedRows = SolvesTable
        .update({ SolvesTable.id eq id }) {
          it[SolvesTable.comment] = newComment
        }
      if (updatedRows > 0) {
        SolvesTable.select { SolvesTable.id eq id }
          .singleOrNull()
          ?.toSolve()
      } else {
        null
      }
    }
  }

  /**
   * Deletes a solve with the specified ID from the SolvesTable.
   * @param id The ID of the solve to delete.
   * @return True if the deletion was successful, false otherwise.
   */
  fun deleteSolve(id: Long): Boolean {
    return transaction {
      SolvesTable
        .deleteWhere { SolvesTable.id eq id } > 0
    }
  }

  /**
   * Converts a database result row to a [Solve] object.
   * @return The converted [Solve] object.
   */
  fun ResultRow.toSolve(): Solve {
    return Solve(
      this[SolvesTable.id].value,
      this[SolvesTable.time],
      this[SolvesTable.scramble],
      this[SolvesTable.penalty],
      this[SolvesTable.comment]
    )
  }
}
