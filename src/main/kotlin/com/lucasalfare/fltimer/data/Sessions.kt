package com.lucasalfare.fltimer.data

import com.lucasalfare.fltimer.model.PuzzleCategory
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object Sessions {

  fun create(name: String, category: PuzzleCategory) {
    transaction {
      SessionsTable.insert {
        it[SessionsTable.name] = name
        it[SessionsTable.puzzleCategory] = category
      }
    }
  }

  fun deleteByName(name: String) {
    transaction {
      SolvesTable.deleteWhere { SolvesTable.sessionName eq name }
      SessionsTable.deleteWhere { SessionsTable.name eq name }
    }
  }

  fun deleteAllSolvesOfSessionByName(targetSessionNameToClear: String) {
    transaction {
      SolvesTable.deleteWhere { SolvesTable.sessionName eq targetSessionNameToClear }
    }
  }
}