package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.config.IntegrationConfig
import com.onlyteo.sandbox.model.Greeting
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class GreetingService(private val integration: IntegrationConfig, private val httpClient: HttpClient) {

    suspend fun getGreeting(name: String?): Greeting {
        val response = httpClient.get(integration.url) {
            if (!name.isNullOrBlank()) {
                url {
                    parameters.append("name", name)
                }
            }
        }
        return response.call.body()
    }
}