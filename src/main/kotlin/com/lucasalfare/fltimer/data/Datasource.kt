@file:Suppress("SameParameterValue")

package com.lucasalfare.fltimer.data

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import com.zaxxer.hikari.util.IsolationLevel

object Datasource {

  private lateinit var datasource: HikariDataSource

  fun getDatasource(): HikariDataSource {
    if (!Datasource::datasource.isInitialized) {
      datasource = createHikariDataSource(
        jdbcUrl = "jdbc:sqlite:data.db",
        username = "FLTimer",
        password = "FLTimerTmpPassword"
      )
    }

    return datasource
  }

  private fun createHikariDataSource(
    jdbcUrl: String,
    username: String,
    password: String
  ): HikariDataSource {
    val hikariConfig = HikariConfig().apply {
      this.jdbcUrl = jdbcUrl
      this.driverClassName = "org.sqlite.JDBC"
      this.username = username
      this.password = password
      this.maximumPoolSize = 5
      this.isAutoCommit = true
      this.transactionIsolation = IsolationLevel.TRANSACTION_READ_COMMITTED.name
      this.validate()
    }

    // Criando uma nova instância de HikariDataSource usando o HikariConfig configurado
    return HikariDataSource(hikariConfig)
  }
}