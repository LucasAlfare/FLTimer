package com.lucasalfare.fltimer.data

import com.lucasalfare.fllistening.EventManageable
import com.lucasalfare.fltimer.Constants
import com.lucasalfare.fltimer.TimerEvent
import kotlinx.coroutines.delay
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DataManager : EventManageable() {
  override suspend fun initialize() {
    initDatabase()

    initialized = true
  }

  /**
   * Here we must handle events of:
   *
   * - Inspection finish;
   * - Timer finish;
   * - SolvesDataModify (CRUD operation of the tables);
   * - Scramble generated;
   * - Preference update;
   */
  override fun onEvent(event: Any, data: Any?) {
    if (event == TimerEvent.TimerFinish) {
      // here we assume that scramble and penalty was previously received; if not, define then as defaults.
      val solveTime = data as Long
    }
  }

  private fun initDatabase(
    dropTablesOnStart: Boolean = false
  ) {
    Datasource.initialize(
      jdbcUrl = System.getenv("DB_JDBC_URL") ?: Constants.SQLITE_URL,
      jdbcDriverClassName = System.getenv("DB_JDBC_DRIVER") ?: Constants.SQLITE_DRIVER,
      username = System.getenv("DB_USERNAME") ?: "",
      password = System.getenv("DB_PASSWORD") ?: ""
    ) {
      if (dropTablesOnStart) {
        SchemaUtils.drop(
          SessionsTable, SolvesTable
        )
      }

      transaction(Datasource.DB) {
        SchemaUtils.createMissingTablesAndColumns(
          SessionsTable, SolvesTable
        )
      }
    }
  }
}
