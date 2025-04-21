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
        val personId = personRepository.upsertPerson(person.name)
        val prefix = prefixRepository.getPrefix()
        val message = "${prefix.greeting} ${person.name}!"
        val result = greetingRepository.insertGreeting(message, personId)
        logger.info("Returning greeting to \"{}\"", person.name)
        return result.toGreeting()
    }

    fun findGreetings(name: String): List<Greeting> {
        val result = greetingRepository.findGreetings(name)
        logger.info("Returning all greetings for \"{}\"", name)
        return result.map { it.toGreeting() }
    }
}