package com.onlyteo.sandbox.app.model

import com.expediagroup.graphql.server.operations.Query
import com.onlyteo.sandbox.app.service.GreetingService

class GreetingQuery(
    private val greetingService: GreetingService
) : Query {
    suspend fun getGreeting(name: String): Greeting {
        return greetingService.getGreeting(Person(name))
    }
}