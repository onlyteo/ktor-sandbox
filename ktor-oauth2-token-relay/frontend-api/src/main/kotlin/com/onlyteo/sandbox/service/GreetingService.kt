package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.config.buildLogger
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.model.UserSession
import com.onlyteo.sandbox.properties.ApplicationProperties
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class GreetingService(
    private val properties: ApplicationProperties,
    private val httpClient: HttpClient
) {
    private val logger = buildLogger

    suspend fun getGreeting(session: UserSession, person: Person): Greeting {
        logger.info("Fetching greeting for \"{}\"", person.name)
        val url = "${properties.integrations.backend.url}/api/greetings"
        val response = httpClient.post(url) {
            bearerAuth(session.token!!)
            contentType(ContentType.Application.Json)
            setBody(person)
        }
        return response.call.body()
    }
}