package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.plugin.restClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class GreetingService(private val url: String) {

    private val client = restClient

    suspend fun getGreeting(name: String?): Greeting {
        val response = client.get(url) {
            if (!name.isNullOrBlank()) {
                url {
                    parameters.append("name", name)
                }
            }
        }
        return response.call.body()
    }
}