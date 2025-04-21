package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.logging.factory.buildLogger
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.repository.PrefixRepository

class GreetingService(
    private val prefixRepository: PrefixRepository
) {
    private val logger = buildLogger

    fun getGreeting(person: Person): Greeting {
        val prefix = prefixRepository.getPrefix()
        val message = "${prefix.greeting} ${person.name}!"
        logger.info("Returning greeting to \"{}\"", person.name)
        return Greeting(message)
    }
}