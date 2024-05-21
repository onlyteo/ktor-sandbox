package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.cache.RequestCache
import com.onlyteo.sandbox.config.Config
import com.onlyteo.sandbox.routes.authRouting
import com.onlyteo.sandbox.routes.greetingRouting
import com.onlyteo.sandbox.routes.staticRouting
import com.onlyteo.sandbox.routes.userRouting
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.IgnoreTrailingSlash
import io.ktor.server.routing.routing

fun Application.configureRouting(
    config: Config,
    httpClient: HttpClient,
    requestCache: RequestCache<String, String>
) {
    install(IgnoreTrailingSlash)
    routing {
        staticRouting()
        authRouting(config, requestCache)
        userRouting()
        greetingRouting(config, httpClient)
    }
}