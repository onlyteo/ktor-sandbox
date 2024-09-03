package com.onlyteo.sandbox.repository

import com.onlyteo.sandbox.model.PersonsTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.upsert

class PersonRepository {

    fun upsertPerson(name: String): Int = transaction {
        val result = PersonsTable.upsert {
            it[PersonsTable.name] = name
        }
        result[PersonsTable.id].value
    }
}