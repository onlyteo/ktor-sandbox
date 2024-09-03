package com.onlyteo.sandbox.repository

import com.onlyteo.sandbox.model.GreetingEntity
import com.onlyteo.sandbox.model.PersonEntity
import com.onlyteo.sandbox.model.PersonsTable
import org.jetbrains.exposed.dao.with
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.transaction

class GreetingRepository {

    fun findGreetings(name: String): List<GreetingEntity> = transaction {
        val personEntity = PersonEntity
            .find { PersonsTable.name eq name }
            .orderBy(PersonsTable.id to SortOrder.ASC)
            .with(PersonEntity::greetings)
            .firstOrNull()
        personEntity?.greetings?.toList() ?: listOf()
    }

    fun insertGreeting(message: String, personEntity: PersonEntity): GreetingEntity = transaction {
        GreetingEntity.new {
            this.message = message
            this.person = personEntity
        }
    }
}