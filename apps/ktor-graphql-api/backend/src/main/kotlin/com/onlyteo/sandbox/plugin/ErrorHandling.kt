package com.onlyteo.sandbox.plugin

import com.expediagroup.graphql.server.ktor.defaultGraphQLStatusPages
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages

fun Application.configureErrorHandling() {
    install(StatusPages) {
        defaultGraphQLStatusPages()
    }
}