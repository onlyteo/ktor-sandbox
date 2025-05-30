package com.onlyteo.sandbox.app.service

import com.onlyteo.sandbox.lib.logging.factory.buildLogger
import com.onlyteo.sandbox.app.model.Greeting
import com.onlyteo.sandbox.app.model.Person
import com.onlyteo.sandbox.app.model.UserSession
import com.onlyteo.sandbox.app.properties.ApplicationProperties
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class GreetingService(
    private val properties: ApplicationProperties,
    private val httpClient: HttpClient
) {
    private val logger = buildLogger

    suspend fun getGreeting(session: UserSession, person: Person): Greeting {
        logger.info("Fetching greeting for \"{}\"", person.name)
        with(properties.integrations) {
            val url = "${backend.url}/api/greetings"
            val response = httpClient.post(url) {
                bearerAuth(session.accessToken.token)
                contentType(ContentType.Application.Json)
                setBody(person)
            }
            return response.call.body()
        }
    }
}