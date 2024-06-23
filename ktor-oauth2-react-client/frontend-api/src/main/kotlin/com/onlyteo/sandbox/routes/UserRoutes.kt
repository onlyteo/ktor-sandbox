package com.onlyteo.sandbox.routes

import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.userRoutes() {
    authenticate("spring-authorization-server") {
        get("/user") {
            call.respondText("Hello World!")
        }
    }
}