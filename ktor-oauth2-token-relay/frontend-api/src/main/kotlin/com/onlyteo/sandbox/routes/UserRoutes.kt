package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.context.ApplicationContext
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.userRoutes(context: ApplicationContext) {
    with(context) {
        authenticate(properties.security.session.name, properties.security.oauth2.name) {
            get("/user") {
                call.respondText("Hello World!")
            }
        }
    }
}