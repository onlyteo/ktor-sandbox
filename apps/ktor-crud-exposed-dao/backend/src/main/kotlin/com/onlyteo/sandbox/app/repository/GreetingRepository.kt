package com.onlyteo.sandbox.app.repository

import com.onlyteo.sandbox.app.model.GreetingEntity
import com.onlyteo.sandbox.app.model.PersonEntity
import com.onlyteo.sandbox.app.model.PersonsTable
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.with
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

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