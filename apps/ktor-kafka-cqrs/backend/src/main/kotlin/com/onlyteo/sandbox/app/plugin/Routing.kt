package com.onlyteo.sandbox.app.plugin

import com.onlyteo.sandbox.app.routes.staticRouting
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.IgnoreTrailingSlash
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    install(IgnoreTrailingSlash)
    routing {
        staticRouting()
    }
}