package com.onlyteo.sandbox.app.context

import com.expediagroup.graphql.client.jackson.GraphQLClientJacksonSerializer
import com.expediagroup.graphql.client.ktor.GraphQLKtorClient
import com.onlyteo.sandbox.app.config.buildRestClient
import com.onlyteo.sandbox.app.properties.ApplicationProperties
import com.onlyteo.sandbox.app.properties.ApplicationPropertiesHolder
import com.onlyteo.sandbox.properties.loadProperties
import com.onlyteo.sandbox.app.service.GreetingService
import io.ktor.client.HttpClient
import java.net.URI

data class ApplicationContext(
    val properties: ApplicationProperties = loadProperties<ApplicationPropertiesHolder>().app,
    val httpClient: HttpClient = buildRestClient(),
    val graphQLClient: GraphQLKtorClient = GraphQLKtorClient(
        url = URI.create("${properties.integrations.backend.url}/graphql").toURL(),
        httpClient = httpClient,
        serializer = GraphQLClientJacksonSerializer()
    ),
    val greetingService: GreetingService = GreetingService(graphQLClient)
)
