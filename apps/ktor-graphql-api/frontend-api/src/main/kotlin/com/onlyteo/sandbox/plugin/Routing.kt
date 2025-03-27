package com.onlyteo.sandbox.plugin

import com.expediagroup.graphql.server.ktor.graphQLGetRoute
import com.expediagroup.graphql.server.ktor.graphQLPostRoute
import com.expediagroup.graphql.server.ktor.graphQLSDLRoute
import com.expediagroup.graphql.server.ktor.graphiQLRoute
import com.onlyteo.sandbox.routes.staticRoutes
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.routing.IgnoreTrailingSlash
import io.ktor.server.routing.routing

const val ROUTING_PLUGIN_NAME = "RoutingPlugin"

fun Application.configureRouting() {
    log.info("Installing {}", ROUTING_PLUGIN_NAME)
    install(IgnoreTrailingSlash)
    routing {
        staticRoutes()
        graphQLGetRoute()
        graphQLPostRoute()
        graphiQLRoute()
        graphQLSDLRoute()
    }
}