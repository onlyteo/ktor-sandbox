package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.logging.factory.buildLogger
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.model.toGreeting
import com.onlyteo.sandbox.repository.GreetingRepository
import com.onlyteo.sandbox.repository.PersonRepository
import com.onlyteo.sandbox.repository.PrefixRepository

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