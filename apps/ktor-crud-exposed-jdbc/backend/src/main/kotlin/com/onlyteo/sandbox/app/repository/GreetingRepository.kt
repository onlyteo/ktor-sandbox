package com.onlyteo.sandbox.app.repository

import com.onlyteo.sandbox.app.model.GreetingsTable
import com.onlyteo.sandbox.app.model.PersonsTable
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.statements.InsertStatement
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

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