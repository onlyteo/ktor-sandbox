package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.config.buildLogger
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.GreetingEntity
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.model.PersonEntity
import com.onlyteo.sandbox.model.PersonsTable
import com.onlyteo.sandbox.model.toGreeting
import org.jetbrains.exposed.dao.with
import org.jetbrains.exposed.sql.transactions.transaction

class GreetingService(private val prefixService: PrefixService) {

    private val logger = buildLogger

    fun getGreeting(person: Person): Greeting = transaction {
        val personEntity = PersonEntity
            .find { PersonsTable.name eq person.name }
            .firstOrNull() ?: PersonEntity.new {
            this.name = person.name
        }
        val prefix = prefixService.getPrefix()
        val message = "${prefix.greeting} ${personEntity.name}!"
        val greetingEntity = GreetingEntity.new {
            this.message = message
            this.person = personEntity
        }
        logger.info("Returning greeting to \"{}\"", personEntity.name)
        greetingEntity.toGreeting()
    }

    fun findGreetings(name: String): List<Greeting> = transaction {
        val personEntity = PersonEntity
            .find { PersonsTable.name eq name }
            .with(PersonEntity::greetings)
            .firstOrNull()
        personEntity?.greetings?.map { Greeting(it.message) } ?: listOf()
    }
}