package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.config.buildRestClient
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class GreetingService(private val httpClient: HttpClient = buildRestClient()) {

    context(ApplicationContext)
    suspend fun getGreeting(person: Person): Greeting {
        val response = httpClient.post(properties.integrations.backend.url) {
            contentType(ContentType.Application.Json)
            setBody(person)
        }
        return response.call.body()
    }
}