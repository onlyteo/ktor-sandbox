package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.context.ApplicationContext
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.metricsRoutes(applicationContext: ApplicationContext) {
    with(applicationContext) {

        get("/metrics") {
            call.respond(prometheusMeterRegistry.scrape())
        }
    }
}