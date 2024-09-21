package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.model.UserSession
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions

fun Route.greetingRoutes(context: ApplicationContext) {
    with(context) {
        authenticate(properties.security.session.name, properties.security.oauth2.name) {
            post("/api/greetings") {
                val session = call.sessions.get<UserSession>()!!
                val person = call.receive<Person>()
                val greeting = greetingService.getGreeting(session, person)
                call.respond(greeting)
            }
        }
    }
}