package com.onlyteo.sandbox.app.routes

import com.onlyteo.sandbox.app.context.ApplicationContext
import com.onlyteo.sandbox.app.model.Person
import com.onlyteo.sandbox.app.model.UserSession
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions

fun Route.greetingRoutes(applicationContext: ApplicationContext) {
    with(applicationContext) {
        with(properties.security) {
            authenticate(session.name, oauth2.name) {
                post<Person>("/api/greetings") { person ->
                    val session = call.sessions.get<UserSession>()!!
                    val greeting = greetingService.getGreeting(session, person)
                    call.respond(greeting)
                }
            }
        }
    }
}