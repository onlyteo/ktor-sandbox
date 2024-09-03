package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.config.buildLogger
import com.onlyteo.sandbox.config.buildRestClient
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class GreetingService(private val httpClient: HttpClient = buildRestClient()) {

    private val logger = buildLogger

    context(ApplicationContext)
    suspend fun getGreeting(person: Person): Greeting {
        logger.info("Fetching greeting for \"{}\"", person.name)
        val url = "${properties.integrations.backend.url}/api/greetings"
        val response = httpClient.post(url) {
            contentType(ContentType.Application.Json)
            setBody(person)
        }
        return response.call.body()
    }
}