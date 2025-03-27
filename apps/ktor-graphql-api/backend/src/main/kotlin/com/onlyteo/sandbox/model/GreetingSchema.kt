package com.onlyteo.sandbox.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.federation.directives.ContactDirective
import com.expediagroup.graphql.server.Schema

@ContactDirective(
    name = "Ktor Sandbox",
    url = "https://github.com/onlyteo/ktor-sandbox",
    description = "Open issues at https://github.com/onlyteo/ktor-sandbox/issues"
)
@GraphQLDescription("GraphQL greeting schema")
class GreetingSchema : Schema