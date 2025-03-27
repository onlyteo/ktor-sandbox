package com.onlyteo.sandbox.plugin

import com.expediagroup.graphql.server.ktor.GraphQL
import com.expediagroup.graphql.server.ktor.KtorGraphQLRequestParser
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.GreetingMutation
import com.onlyteo.sandbox.model.GreetingQuery
import com.onlyteo.sandbox.model.GreetingSchema
import com.onlyteo.sandbox.serialization.factory.buildObjectMapper
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log

const val GRAPHQL_PLUGIN_NAME = "GraphQLPlugin"

fun Application.configureGraphQL(
    applicationContext: ApplicationContext
) {
    with(applicationContext) {
        log.info("Installing {}", GRAPHQL_PLUGIN_NAME)
        install(GraphQL) {
            schema {
                packages = listOf("com.onlyteo.sandbox")
                queries = listOf(GreetingQuery(greetingService))
                mutations = listOf(GreetingMutation(greetingService))
                schemaObject = GreetingSchema()
            }
            server {
                requestParser = KtorGraphQLRequestParser(buildObjectMapper)
            }
        }
    }
}