package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.routes.greetingRoutes
import com.onlyteo.sandbox.routes.staticRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(context: ApplicationContext) {
    install(IgnoreTrailingSlash)
    routing {
        staticRoutes()
        greetingRoutes(context)
    }
}