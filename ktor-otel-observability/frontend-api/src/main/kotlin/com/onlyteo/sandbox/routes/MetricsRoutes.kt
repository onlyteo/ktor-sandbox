package com.onlyteo.sandbox.routes

import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.micrometer.prometheus.PrometheusMeterRegistry

fun Route.metricsRoutes(prometheusMeterRegistry: PrometheusMeterRegistry) {
    get("/metrics") {
        call.respond(prometheusMeterRegistry.scrape())
    }
}