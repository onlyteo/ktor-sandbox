package com.onlyteo.sandbox.app.service

import com.onlyteo.sandbox.lib.logging.factory.buildLogger
import com.onlyteo.sandbox.app.model.Greeting
import com.onlyteo.sandbox.app.model.Person
import com.onlyteo.sandbox.app.properties.ApplicationProperties
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class GreetingService(
    private val properties: ApplicationProperties,
    private val client: HttpClient
) {
    private val logger = buildLogger

    suspend fun getGreeting(person: Person): Greeting {
        logger.info("Fetching greeting for \"{}\"", person.name)
        val url = "${properties.integrations.backend.url}/api/greetings"
        val response = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(person)
        }
        return response.call.body()
    }

    suspend fun getGreetings(name: String): List<Greeting> {
        logger.info("Fetching greetings for \"{}\"", name)
        val url = "${properties.integrations.backend.url}/api/greetings"
        val response = client.get(url) {
            url {
                parameters.append("name", name)
            }
        }
        return response.call.body()
    }
}