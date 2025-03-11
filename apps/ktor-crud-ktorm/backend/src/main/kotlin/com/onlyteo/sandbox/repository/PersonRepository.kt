package com.onlyteo.sandbox.repository

import com.onlyteo.sandbox.model.PersonEntity
import com.onlyteo.sandbox.model.persons
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find

class PersonRepository(private val database: Database) {

    fun findPerson(name: String): PersonEntity? {
        return database.persons.find { it.name eq name }
    }

    fun insertPerson(name: String): PersonEntity {
        database.useTransaction {
            val personEntity = PersonEntity {
                this.name = name
            }
            database.persons.add(personEntity)
                .let { if (it == 0) throw IllegalStateException("Could not insert person") }
        }
        return database.persons.find { it.name eq name } ?: throw IllegalStateException("Could not insert person")
    }
}