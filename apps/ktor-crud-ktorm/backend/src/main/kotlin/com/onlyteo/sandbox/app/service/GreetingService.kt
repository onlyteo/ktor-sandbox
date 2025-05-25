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
        val personEntity = personRepository.findPerson(person.name) ?: personRepository.insertPerson(person.name)
        val prefix = prefixRepository.getPrefix()
        val message = "${prefix.greeting} ${personEntity.name}!"
        val greetingEntity = greetingRepository.insertGreeting(message, personEntity)
        logger.info("Returning greeting to \"{}\"", personEntity.name)
        return greetingEntity.toGreeting()
    }

    fun findGreetings(name: String): List<Greeting> {
        val greetingEntities = greetingRepository.findGreetings(name)
        logger.info("Returning all greetings for \"{}\"", name)
        return greetingEntities.map { it.toGreeting() }
    }
}