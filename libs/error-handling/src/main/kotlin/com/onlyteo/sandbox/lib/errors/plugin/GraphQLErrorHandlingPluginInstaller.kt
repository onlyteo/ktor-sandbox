package com.onlyteo.sandbox.lib.errors.plugin

import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.configureGraphQLErrorHandling() {
    install(GraphQLErrorHandlingPlugin)
}