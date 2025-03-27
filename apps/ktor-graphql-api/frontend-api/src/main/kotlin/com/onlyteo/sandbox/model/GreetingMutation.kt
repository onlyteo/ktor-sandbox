package com.onlyteo.sandbox.model

import com.expediagroup.graphql.server.operations.Mutation
import com.onlyteo.sandbox.service.GreetingService

class GreetingMutation(
    private val greetingService: GreetingService
) : Mutation {
    suspend fun postGreeting(person: Person): Greeting {
        return greetingService.getGreeting(person)
    }
}