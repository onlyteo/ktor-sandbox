package com.onlyteo.sandbox.reposiitory

import com.onlyteo.sandbox.model.PersonEntity
import com.onlyteo.sandbox.model.PersonsTable
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