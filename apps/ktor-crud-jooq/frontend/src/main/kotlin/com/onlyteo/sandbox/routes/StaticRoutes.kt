package com.onlyteo.sandbox.routes

import io.ktor.server.http.content.resolveResource
import io.ktor.server.http.content.staticResources
import io.ktor.server.plugins.NotFoundException
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.staticRoutes() {
    staticResources("/assets/", "static/assets")

    get("/favicon.ico") {
        val content = call.resolveResource("/static/favicon.ico") ?: throw NotFoundException()
        call.respond(content)
    }
}