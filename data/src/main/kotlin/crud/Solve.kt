package crud

import Solve
import SolveTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

fun createSolve(time: Long, scramble: String, comment: String?, penalty: String) {
  transaction {
    SolveTable.insertAndGetId {
      it[SolveTable.time] = time
      it[SolveTable.scramble] = scramble
      it[SolveTable.comment] = comment
      it[SolveTable.penalty] = penalty
    }
  }
}

fun getSolves(): List<Solve> {
  return transaction {
    SolveTable.selectAll().map {
      Solve(
        it[SolveTable.id].value,
        it[SolveTable.time],
        it[SolveTable.scramble],
        it[SolveTable.comment],
        it[SolveTable.penalty]
      )
    }
  }
}

fun getSolveById(id: Int): Solve? {
  return transaction {
    SolveTable.select { SolveTable.id eq id }.singleOrNull()?.let {
      Solve(
        it[SolveTable.id].value,
        it[SolveTable.time],
        it[SolveTable.scramble],
        it[SolveTable.comment],
        it[SolveTable.penalty]
      )
    }
  }
}

fun updateSolve(id: Int, nextTime: Long, nextScramble: String, nextComment: String?, nextPenalty: String) {
  transaction {
    SolveTable.update({ SolveTable.id eq id }) {
      it[time] = nextTime
      it[scramble] = nextScramble
      it[comment] = nextComment
      it[penalty] = nextPenalty
    }
  }
}

fun removeSolve(id: Int) {
  transaction {
    SolveTable.deleteWhere { SolveTable.id eq id }
  }
}