package com.onlyteo.sandbox.app.service

import com.onlyteo.sandbox.lib.logging.factory.buildLogger
import com.onlyteo.sandbox.app.model.Greeting
import com.onlyteo.sandbox.app.model.Person
import com.onlyteo.sandbox.app.repository.PrefixRepository
import io.opentelemetry.api.trace.Span
import io.opentelemetry.instrumentation.annotations.WithSpan

class GreetingService(
    private val prefixRepository: PrefixRepository
) {
    private val logger = buildLogger

    @WithSpan("greeting.service")
    fun getGreeting(person: Person): Greeting {
        try {
            Span.current().addEvent("before")
            val prefix = prefixRepository.getPrefix()
            val message = "${prefix.greeting} ${person.name}!"
            logger.info("Returning greeting to \"{}\"", person.name)
            return Greeting(message)
        } finally {
            Span.current().addEvent("after")
        }
    }
}