package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.context.LoggingContext
import com.onlyteo.sandbox.routes.greetingRoutes
import com.onlyteo.sandbox.routes.staticRoutes
import com.onlyteo.sandbox.service.GreetingService
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.IgnoreTrailingSlash
import io.ktor.server.routing.routing

context(LoggingContext)
fun Application.configureRouting(greetingService: GreetingService) {
    install(IgnoreTrailingSlash)
    routing {
        staticRoutes()
        greetingRoutes(greetingService)
    }
}