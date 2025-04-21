package com.onlyteo.sandbox.service

import com.expediagroup.graphql.client.ktor.GraphQLKtorClient
import com.onlyteo.sandbox.exception.GreetingFailedException
import com.onlyteo.sandbox.logging.factory.buildLogger
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
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