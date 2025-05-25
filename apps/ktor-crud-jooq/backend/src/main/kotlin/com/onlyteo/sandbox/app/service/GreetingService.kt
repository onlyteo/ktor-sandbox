package com.onlyteo.sandbox.app.service

import com.onlyteo.sandbox.lib.logging.factory.buildLogger
import com.onlyteo.sandbox.app.model.Greeting
import com.onlyteo.sandbox.app.model.Person
import com.onlyteo.sandbox.app.model.toGreeting
import com.onlyteo.sandbox.app.repository.GreetingRepository
import com.onlyteo.sandbox.app.repository.PersonRepository
import com.onlyteo.sandbox.app.repository.PrefixRepository

class GreetingService(
    private val prefixRepository: PrefixRepository,
    private val personRepository: PersonRepository,
    private val greetingRepository: GreetingRepository
) {
    private val logger = buildLogger

    fun getGreeting(person: Person): Greeting {
        val prefix = prefixRepository.getPrefix()
        val personRecord = personRepository.findPerson(person.name) ?: personRepository.insertPerson(person.name)
        val message = "${prefix.greeting} ${personRecord.name}!"
        val greetingsRecord = greetingRepository.insertGreeting(
            message,
            personRecord.id ?: throw IllegalStateException("ID field is null")
        )
        logger.info("Returning greeting to \"{}\"", personRecord.name)
        return greetingsRecord.toGreeting()
    }

    fun findGreetings(name: String): List<Greeting> {
        val greetingsRecords = greetingRepository.findGreetings(name)
        logger.info("Returning all greetings for \"{}\"", name)
        return greetingsRecords.map { it.toGreeting() }
    }
}