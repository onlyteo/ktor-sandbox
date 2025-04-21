package com.onlyteo.sandbox.errors.plugin

import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.configureGraphQLErrorHandling() {
    install(GraphQLErrorHandlingPlugin)
}