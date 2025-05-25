package com.onlyteo.sandbox.app.repository

import com.onlyteo.sandbox.app.model.GreetingsTable
import com.onlyteo.sandbox.app.model.PersonsTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction

class GreetingRepository {

    fun findGreetings(name: String): List<ResultRow> = transaction {
        GreetingsTable.innerJoin(PersonsTable)
            .selectAll()
            .where { PersonsTable.name eq name }
            .toList()
    }

    fun insertGreeting(message: String, personId: Int): InsertStatement<Number> = transaction {
        GreetingsTable.insert {
            it[GreetingsTable.message] = message
            it[GreetingsTable.personId] = personId
        }
    }
}