package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.routes.authRouting
import com.onlyteo.sandbox.routes.staticRouting
import com.onlyteo.sandbox.routes.userRouting
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRouting(redirects: MutableMap<String, String>) {
    routing {
        staticRouting()
        authRouting(redirects)
        userRouting()
    }
}