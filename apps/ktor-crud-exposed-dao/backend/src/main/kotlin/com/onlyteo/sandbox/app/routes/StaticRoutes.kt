package com.onlyteo.sandbox.app.routes

import io.ktor.server.http.content.staticResources
import io.ktor.server.routing.Route

fun Route.staticRoutes() {
    staticResources("/", "static")
}