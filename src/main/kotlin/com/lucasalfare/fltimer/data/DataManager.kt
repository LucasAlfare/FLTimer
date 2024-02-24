package com.lucasalfare.fltimer.data

import com.lucasalfare.fllistening.EventManageable
import com.lucasalfare.fltimer.TimerEvent
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

lateinit var currentDatasource: Database

class DataManager : EventManageable() {
  override suspend fun initialize() {
    newSuspendedTransaction(db = Database.connect(Datasource.getDatasource())) {
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
