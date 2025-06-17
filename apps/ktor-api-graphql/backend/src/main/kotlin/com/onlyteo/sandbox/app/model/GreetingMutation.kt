package com.onlyteo.sandbox.app.model

import com.expediagroup.graphql.server.operations.Mutation
import com.onlyteo.sandbox.app.service.GreetingService

class GreetingMutation(
    private val greetingService: GreetingService
) : Mutation {
    fun postGreeting(person: Person): Greeting {
        return greetingService.getGreeting(person)
    }
}