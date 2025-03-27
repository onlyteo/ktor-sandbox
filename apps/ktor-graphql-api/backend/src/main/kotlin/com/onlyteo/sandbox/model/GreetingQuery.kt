package com.onlyteo.sandbox.model

import com.expediagroup.graphql.server.operations.Query
import com.onlyteo.sandbox.service.GreetingService

class GreetingQuery(
    private val greetingService: GreetingService
) : Query {
    fun getGreeting(name: String): Greeting {
        return greetingService.getGreeting(Person(name))
    }
}