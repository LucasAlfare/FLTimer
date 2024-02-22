package com.lucasalfare.fltimer.data

import com.lucasalfare.fltimer.model.Penalty
import com.lucasalfare.fltimer.model.Solve
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object Solves {

  fun create(
    time: Long = 0L,
    scramble: String = "",
    penalty: Penalty = Penalty.Ok,
    comment: String = "",
    targetSession: String
  ) {
    transaction {
      SolvesTable.insert {
        it[SolvesTable.time] = time
        it[SolvesTable.scramble] = scramble
        it[SolvesTable.penalty] = penalty
        it[SolvesTable.comment] = comment
        it[SolvesTable.sessionName] = targetSession
      }
    }
  }

  fun getBySessionName(sessionName: String): List<Solve> {
    return transaction {
      SolvesTable.select { SolvesTable.sessionName eq sessionName }
        .map { rowToSolve(it) }
    }
  }

  fun getByPenalty(penalty: Penalty): List<Solve> {
    return transaction {
      SolvesTable.select { SolvesTable.penalty eq penalty }
        .map { rowToSolve(it) }
    }
  }

  fun getByComment(comment: String): List<Solve> {
    return transaction {
      SolvesTable.select { SolvesTable.comment eq comment }
        .map { rowToSolve(it) }
    }
  }

  // Note: null value indicates that the current existing value should not be changed.
  // In other words, the specific value will only be updated if it is not null in the args.
  fun update(
    targetSolveId: Long,
    time: Long? = null,
    scramble: String? = null,
    penalty: Penalty? = null,
    comment: String? = null
  ) {
    transaction {
      SolvesTable.update({ SolvesTable.id eq targetSolveId }) {
        time?.let { updatingValue -> it[SolvesTable.time] = updatingValue }
        scramble?.let { updatingValue -> it[SolvesTable.scramble] = updatingValue }
        penalty?.let { updatingValue -> it[SolvesTable.penalty] = updatingValue }
        comment?.let { updatingValue -> it[SolvesTable.comment] = updatingValue }
      }
    }
  }

  fun deleteById(id: Long) {
    transaction {
      SolvesTable.deleteWhere { SolvesTable.id eq id }
    }
  }

  fun deleteByPenalty(penalty: Penalty) {
    transaction {
      SolvesTable.deleteWhere { SolvesTable.penalty eq penalty }
    }
  }

  private fun rowToSolve(row: ResultRow): Solve {
    return Solve(
      row[SolvesTable.id].value,
      row[SolvesTable.time],
      row[SolvesTable.scramble],
      row[SolvesTable.penalty],
      row[SolvesTable.comment]
    )
  }
}