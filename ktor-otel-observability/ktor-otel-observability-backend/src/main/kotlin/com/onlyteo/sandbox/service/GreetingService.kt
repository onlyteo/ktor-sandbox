package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.context.LoggingContext
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.model.toGreeting

class GreetingService {

    context(LoggingContext)
    fun getGreeting(person: Person): Greeting {
        logger.info("Returning greeting to \"{}\"", person.name)
        return person.toGreeting()
    }
}