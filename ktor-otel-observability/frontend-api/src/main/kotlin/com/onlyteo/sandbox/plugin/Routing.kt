package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.routes.greetingRoutes
import com.onlyteo.sandbox.routes.metricsRoutes
import com.onlyteo.sandbox.routes.staticRoutes
import com.onlyteo.sandbox.service.GreetingService
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.IgnoreTrailingSlash
import io.ktor.server.routing.routing
import io.micrometer.prometheus.PrometheusMeterRegistry

context(ApplicationContext)
fun Application.configureRouting(
    prometheusMeterRegistry: PrometheusMeterRegistry,
    greetingService: GreetingService
) {
    install(IgnoreTrailingSlash)
    routing {
        staticRoutes()
        metricsRoutes(prometheusMeterRegistry)
        greetingRoutes(greetingService)
    }
}