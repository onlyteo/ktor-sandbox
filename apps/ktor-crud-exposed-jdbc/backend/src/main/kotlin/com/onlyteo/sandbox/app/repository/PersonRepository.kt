package com.onlyteo.sandbox.app.repository

import com.onlyteo.sandbox.app.model.PersonsTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class PersonRepository {

    fun upsertPerson(name: String): Int = transaction {
        val resultRow = PersonsTable.selectAll()
            .where { PersonsTable.name eq name }
            .singleOrNull()
        if (resultRow != null) {
            val id = resultRow[PersonsTable.id].value
            PersonsTable.update(where = { PersonsTable.id eq id }) {
                it[PersonsTable.name] = name
            }
            id
        } else {
            val result = PersonsTable.insert {
                it[PersonsTable.name] = name
            }
            result[PersonsTable.id].value
        }
    }
}