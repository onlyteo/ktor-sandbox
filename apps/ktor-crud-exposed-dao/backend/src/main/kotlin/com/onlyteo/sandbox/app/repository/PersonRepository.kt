package com.onlyteo.sandbox.app.repository

import com.onlyteo.sandbox.app.model.PersonEntity
import com.onlyteo.sandbox.app.model.PersonsTable
import org.jetbrains.exposed.sql.transactions.transaction

class PersonRepository {

    fun findPerson(name: String): PersonEntity? = transaction {
        PersonEntity
            .find { PersonsTable.name eq name }
            .firstOrNull()
    }

    fun insertPerson(name: String): PersonEntity = transaction {
        PersonEntity.new {
            this.name = name
        }
    }
}