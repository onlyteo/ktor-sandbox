package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.routes.greetingRouting
import com.onlyteo.sandbox.routes.staticRouting
import com.onlyteo.sandbox.service.GreetingService
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.IgnoreTrailingSlash
import io.ktor.server.routing.routing

fun Application.configureRouting(greetingService: GreetingService) {
    install(IgnoreTrailingSlash)
    routing {
        staticRouting()
        greetingRouting(greetingService)
    }
}