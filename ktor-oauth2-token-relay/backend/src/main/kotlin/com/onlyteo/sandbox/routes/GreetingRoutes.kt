package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.properties.ApplicationProperties
import com.onlyteo.sandbox.service.GreetingService
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.greetingRoutes(
    properties: ApplicationProperties,
    greetingService: GreetingService
) {
    authenticate(properties.security.oauth2.name) {
        post("/api/greetings") {
            val person = call.receive<Person>()
            val greeting = greetingService.getGreeting(person)
            call.respond(greeting)
        }
    }
}