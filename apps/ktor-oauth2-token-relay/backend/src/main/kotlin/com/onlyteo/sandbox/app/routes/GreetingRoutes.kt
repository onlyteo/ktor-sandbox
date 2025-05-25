package com.onlyteo.sandbox.app.routes

import com.onlyteo.sandbox.app.context.ApplicationContext
import com.onlyteo.sandbox.app.model.Person
import com.onlyteo.sandbox.app.plugin.authorize
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.greetingRoutes(applicationContext: ApplicationContext) {
    with(applicationContext) {
        authenticate(properties.security.oauth2.name) {
            authorize {
                post<Person>("/api/greetings") { person ->
                    authorize {
                        val greeting = greetingService.getGreeting(person)
                        call.respond(greeting)
                    }
                }
            }
        }
    }
}