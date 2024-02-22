package com.lucasalfare.fltimer.data

import com.lucasalfare.fllistening.EventManageable
import com.lucasalfare.fltimer.TimerEvent
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DataManager : EventManageable() {
  override suspend fun initialize() {
    Database.connect("jdbc:sqlite:/data/data.db", "org.sqlite.JDBC")

    newSuspendedTransaction {
      SchemaUtils.createMissingTablesAndColumns(
        SessionsTable, SolvesTable
      )
    }

    initialized = true
  }

  /**
   * Here we must handle events of:
   *
   * - Inspection finish;
   * - Timer finish;
   * - SolvesDataModify;
   * - Scramble generated;
   * - Preference update;
   */
  override fun onEvent(event: Any, data: Any?) {
    if (event == TimerEvent.TimerFinish) {
      val solveTime = data as Long
    }
  }
}
