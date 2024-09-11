package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.properties.ApplicationProperties
import com.onlyteo.sandbox.routes.greetingRoutes
import com.onlyteo.sandbox.routes.staticRoutes
import com.onlyteo.sandbox.service.GreetingService
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.IgnoreTrailingSlash
import io.ktor.server.routing.routing

fun Application.configureRouting(
    properties: ApplicationProperties,
    greetingService: GreetingService
) {
    install(IgnoreTrailingSlash)
    routing {
        staticRoutes()
        greetingRoutes(properties, greetingService)
    }
}