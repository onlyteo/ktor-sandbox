package com.onlyteo.sandbox.app.service

import com.expediagroup.graphql.client.ktor.GraphQLKtorClient
import com.onlyteo.sandbox.app.exception.GreetingFailedException
import com.onlyteo.sandbox.lib.logging.factory.buildLogger
import com.onlyteo.sandbox.app.model.Greeting
import com.onlyteo.sandbox.app.model.Person
import com.onlyteo.sandbox.schema.GetGreeting

class GreetingService(
    private val graphQLClient: GraphQLKtorClient
) {
    private val logger = buildLogger

    suspend fun getGreeting(person: Person): Greeting {
        logger.info("Fetching greeting for \"{}\"", person.name)
        val query = GetGreeting(GetGreeting.Variables(name = person.name))
        val response = graphQLClient.execute(query)
        return response.data?.getGreeting?.let { Greeting(it.message) }
            ?: throw GreetingFailedException("Could not fetch greeting")
    }
}