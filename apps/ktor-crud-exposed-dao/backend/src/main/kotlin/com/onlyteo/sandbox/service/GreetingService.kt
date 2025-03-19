package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.model.toGreeting
import com.onlyteo.sandbox.repository.GreetingRepository
import com.onlyteo.sandbox.repository.PersonRepository
import com.onlyteo.sandbox.repository.PrefixRepository
import no.onlyteo.sandbox.logging.factory.buildLogger

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