package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.cache.RequestCache
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.routes.authRouting
import com.onlyteo.sandbox.routes.greetingRoutes
import com.onlyteo.sandbox.routes.staticRoutes
import com.onlyteo.sandbox.routes.userRoutes
import com.onlyteo.sandbox.service.GreetingService
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.IgnoreTrailingSlash
import io.ktor.server.routing.routing

context(ApplicationContext)
fun Application.configureRouting(
    requestCache: RequestCache<String, String>,
    greetingService: GreetingService
) {
    install(IgnoreTrailingSlash)
    routing {
        staticRoutes()
        authRouting(requestCache)
        userRoutes()
        greetingRoutes(greetingService)
    }
}