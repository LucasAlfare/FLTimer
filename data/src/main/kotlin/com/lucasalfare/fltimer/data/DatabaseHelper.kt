package com.lucasalfare.fltimer.data

import Penalty
import com.lucasalfare.fltimer.data.SessionsTable.name
import com.lucasalfare.fltimer.data.SolvesTable.comment
import com.lucasalfare.fltimer.data.SolvesTable.penalty
import com.lucasalfare.fltimer.data.SolvesTable.scramble
import com.lucasalfare.fltimer.data.SolvesTable.sessionId
import com.lucasalfare.fltimer.data.SolvesTable.time
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection


/**
 * Constant representing the URL for the SQLite database used in the application.
 */
const val DATABASE_URL = "jdbc:sqlite:fltimer_data.db"

/**
 * Represents the SessionsTable in the database schema.
 * @property name Column representing the name or description of a session.
 */
object SessionsTable : IntIdTable("SessionsTable") {
  val name = text("name")
}

/**
 * Represents the SolvesTable in the database schema.
 * @property time Column representing the time taken to complete a solve in milliseconds.
 * @property scramble Column representing the scramble algorithm for a solve.
 * @property penalty Column representing the penalty applied to a solve, using the [Penalty] enum.
 * @property comment Column representing additional comments or notes about a solve.
 * @property sessionId Column representing the foreign key reference to the SessionsTable.
 */
object SolvesTable : LongIdTable("SolvesTable") {
  val time = long("time")
  val scramble = text("scramble")
  val penalty = enumeration("penalty", Penalty::class)
  val comment = text("comment")
  val sessionId = integer("session_id").references(SessionsTable.id)
}

/**
 * Initializes the database with the specified target URL.
 * @param targetUrl The URL of the target database, including the database type and file path.
 */
fun initDatabase(targetUrl: String) {
  Database.connect(targetUrl, driver = "org.sqlite.JDBC")
  TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_READ_UNCOMMITTED
  transaction {
    // Creates missing tables and columns based on the defined schema in SessionsTable and SolvesTable.
    SchemaUtils.createMissingTablesAndColumns(SessionsTable, SolvesTable)
  }
}