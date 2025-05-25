package com.onlyteo.sandbox.app.repository

import com.onlyteo.sandbox.app.model.GreetingEntity
import com.onlyteo.sandbox.app.model.PersonEntity
import com.onlyteo.sandbox.app.model.PersonsTable
import com.onlyteo.sandbox.app.model.greetings
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.filter
import org.ktorm.entity.last
import org.ktorm.entity.toList

class GreetingRepository(private val database: Database) {

    fun findGreetings(name: String): List<GreetingEntity> {
        return database.greetings
            .filter {
                val personTable = it.personId.referenceTable as PersonsTable
                personTable.name eq name
            }.toList()
    }

    fun insertGreeting(message: String, personEntity: PersonEntity): GreetingEntity {
        database.useTransaction {
            val greetingEntity = GreetingEntity {
                this.message = message
                this.person = personEntity
            }
            database.greetings.add(greetingEntity)
                .let { if (it == 0) throw IllegalStateException("Could not insert greeting") }
        }
        return database.greetings.last()
    }
}