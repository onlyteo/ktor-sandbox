package com.onlyteo.sandbox.app.plugin

import com.onlyteo.sandbox.app.context.ApplicationContext
import com.onlyteo.sandbox.app.routes.staticRoutes
import com.onlyteo.sandbox.app.routes.thymeleafRoutes
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.IgnoreTrailingSlash
import io.ktor.server.routing.routing

fun Application.configureRouting(applicationContext: ApplicationContext) {
    install(IgnoreTrailingSlash)
    routing {
        staticRoutes()
        thymeleafRoutes(applicationContext)
    }
}