package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.model.toGreeting
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.greetingRoutes() {
    post("/api/greetings") {
        val person = call.receive<Person>()
        call.respond(person.toGreeting())
    }
}