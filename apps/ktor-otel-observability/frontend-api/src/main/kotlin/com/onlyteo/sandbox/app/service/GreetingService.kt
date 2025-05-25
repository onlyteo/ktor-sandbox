package com.onlyteo.sandbox.app.service

import com.onlyteo.sandbox.lib.logging.factory.buildLogger
import com.onlyteo.sandbox.app.model.Greeting
import com.onlyteo.sandbox.app.model.Person
import com.onlyteo.sandbox.app.properties.ApplicationProperties
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.opentelemetry.api.trace.Span
import io.opentelemetry.instrumentation.annotations.WithSpan

class GreetingService(
    private val properties: ApplicationProperties,
    private val client: HttpClient
) {
    private val logger = buildLogger

    @WithSpan("greeting.service")
    suspend fun getGreeting(person: Person): Greeting {
        try {
            logger.info("Fetching greeting for \"{}\"", person.name)
            val url = "${properties.integrations.backend.url}/api/greetings"
            Span.current().addEvent("before")
            val response = client.post(url) {
                contentType(ContentType.Application.Json)
                setBody(person)
            }
            return response.call.body()
        } finally {
            Span.current().addEvent("after")
        }
    }
}