package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.context.ApplicationContext
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.metricsRoutes(context: ApplicationContext) {
    val prometheusMeterRegistry = context.prometheusMeterRegistry

    get("/metrics") {
        call.respond(prometheusMeterRegistry.scrape())
    }
}