package com.lucasalfare.fltimer.data

import com.lucasalfare.fltimer.model.PuzzleCategory
import com.lucasalfare.fltimer.model.Session
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object Sessions {

  init {
    create(DEFAULT_SESSION_NAME, PuzzleCategory.PocketCube)
  }

  fun create(name: String, category: PuzzleCategory) {
    transaction(Database.connect(Datasource.getDatasource())) {
      SessionsTable.insertIgnore {
        it[SessionsTable.name] = name
        it[SessionsTable.puzzleCategory] = category
      }
    }
  }

  fun getAll() = transaction(Database.connect(Datasource.getDatasource())) {
    SessionsTable.selectAll().map {
      Session(
        it[SessionsTable.id].value,
        it[SessionsTable.name],
        it[SessionsTable.puzzleCategory]
      )
    }
  }

  fun getByName(name: String) = transaction((Database.connect(Datasource.getDatasource()))) {
    SessionsTable.select { SessionsTable.name eq name }.map {
      Session(
        it[SessionsTable.id].value,
        it[SessionsTable.name],
        it[SessionsTable.puzzleCategory]
      )
    }
  }

  fun deleteByName(name: String) {
    transaction((Database.connect(Datasource.getDatasource()))) {
      SolvesTable.deleteWhere { SolvesTable.sessionName eq name }
      SessionsTable.deleteWhere { SessionsTable.name eq name }
    }
  }

  fun deleteAllSolvesOfSessionByName(targetSessionNameToClear: String) {
    transaction((Database.connect(Datasource.getDatasource()))) {
      SolvesTable.deleteWhere { SolvesTable.sessionName eq targetSessionNameToClear }
    }
  }
}