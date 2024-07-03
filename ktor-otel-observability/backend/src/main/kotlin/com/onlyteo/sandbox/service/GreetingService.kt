package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.config.buildLogger
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.repository.PrefixRepository
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