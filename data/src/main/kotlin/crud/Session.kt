package crud

import Session
import SessionTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

// CRUD para Session
fun createSession(name: String) {
  transaction {
    SessionTable.insertAndGetId {
      it[SessionTable.name] = name
    }
  }
}

fun getSessions(): List<Session> {
  return transaction {
    SessionTable.selectAll().map {
      Session(
        it[SessionTable.id].value,
        it[SessionTable.name]
      )
    }
  }
}

fun getSessionById(id: Int): Session? {
  return transaction {
    SessionTable.select { SessionTable.id eq id }.singleOrNull()?.let {
      Session(
        it[SessionTable.id].value,
        it[SessionTable.name]
      )
    }
  }
}

fun updateSession(id: Int, novoNome: String) {
  transaction {
    SessionTable.update({ SessionTable.id eq id }) {
      it[name] = novoNome
    }
  }
}

fun deleteSession(id: Int) {
  transaction {
    SessionTable.deleteWhere { SessionTable.id eq id }
  }
}