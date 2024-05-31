package com.lucasalfare.fltimer.data

import com.lucasalfare.fllistening.EventManageable
import com.lucasalfare.fltimer.TimerEvent
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DataManager : EventManageable() {
  override suspend fun initialize() {
    newSuspendedTransaction(db = Datasource.DB) {
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
}
