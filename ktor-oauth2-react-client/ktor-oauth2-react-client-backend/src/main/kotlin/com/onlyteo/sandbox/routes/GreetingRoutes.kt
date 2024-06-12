package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.model.Greeting
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.greetingRouting() {
    get("/api/greetings") {
        val name = call.parameters["name"] ?: "Nobody"
        call.respond(Greeting("Hello $name!"))
    }
}