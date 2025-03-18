package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.routes.authRouting
import com.onlyteo.sandbox.routes.greetingRoutes
import com.onlyteo.sandbox.routes.staticRoutes
import com.onlyteo.sandbox.routes.userRoutes
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.IgnoreTrailingSlash
import io.ktor.server.routing.routing

fun Application.configureRouting(applicationContext: ApplicationContext) {
    install(IgnoreTrailingSlash)
    routing {
        staticRoutes()
        authRouting(applicationContext)
        userRoutes(applicationContext)
        greetingRoutes(applicationContext)
    }
}