package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.config.buildLogger
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.model.toGreeting
import io.opentelemetry.api.trace.Span
import io.opentelemetry.instrumentation.annotations.WithSpan

class GreetingService {

    private val logger = buildLogger

    @WithSpan("greeting.service")
    fun getGreeting(person: Person): Greeting {
        try {
            logger.info("Returning greeting to \"{}\"", person.name)
            Span.current().addEvent("before")
            return person.toGreeting()
        } finally {
            Span.current().addEvent("after")
        }
    }
}