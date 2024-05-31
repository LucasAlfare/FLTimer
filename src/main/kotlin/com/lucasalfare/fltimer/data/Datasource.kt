@file:Suppress("SameParameterValue")

package com.lucasalfare.fltimer.data

import com.lucasalfare.fltimer.Constants
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import com.zaxxer.hikari.util.IsolationLevel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object Datasource {

  private lateinit var hikariDataSource: HikariDataSource

  val DB by lazy { Database.connect(hikariDataSource) }

  fun initialize(
    jdbcUrl: String,
    jdbcDriverClassName: String,
    username: String,
    password: String,
    onFirstTransactionCallback: () -> Unit = {}
  ) {
    // Creating HikariDataSource using provided parameters.
    hikariDataSource = createHikariDataSource(
      jdbcUrl = jdbcUrl,
      jdbcDriverClassName = jdbcDriverClassName,
      username = username,
      password = password
    )

    // Opening a transaction to ensure the database connection is valid.
    transaction(DB) {
      onFirstTransactionCallback()
    }
  }

  fun shutdown() {
    hikariDataSource.close()
  }

  suspend fun <T> query(queryCodeBlock: suspend () -> T): T =
    newSuspendedTransaction(context = Dispatchers.IO, db = DB) {
      queryCodeBlock()
    }

  private fun createHikariDataSource(
    jdbcUrl: String,
    jdbcDriverClassName: String,
    username: String,
    password: String
  ): HikariDataSource {
    // Configuring HikariCP with provided parameters.
    val hikariConfig = HikariConfig().apply {
      if (jdbcDriverClassName == Constants.SQLITE_DRIVER)
        this.transactionIsolation = IsolationLevel.TRANSACTION_SERIALIZABLE.name

      this.jdbcUrl = jdbcUrl
      this.driverClassName = jdbcDriverClassName
      this.username = username
      this.password = password
      this.maximumPoolSize = 6
      this.isAutoCommit = true
      this.validate()
    }

    // Creating and returning HikariDataSource.
    return HikariDataSource(hikariConfig)
  }
}