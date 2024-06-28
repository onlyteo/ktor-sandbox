package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.config.buildLogger
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.model.toGreeting

class GreetingService {

    private val logger = buildLogger

    fun getGreeting(person: Person): Greeting {
        logger.info("Returning greeting to \"{}\"", person.name)
        return person.toGreeting()

    }
}