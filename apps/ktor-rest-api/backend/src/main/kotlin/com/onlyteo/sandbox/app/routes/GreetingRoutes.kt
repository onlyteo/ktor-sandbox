package com.onlyteo.sandbox.app.routes

import com.onlyteo.sandbox.app.context.ApplicationContext
import com.onlyteo.sandbox.app.model.Person
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.greetingRoutes(applicationContext: ApplicationContext) {
    with(applicationContext) {

        post<Person>("/api/greetings") { person ->
            val greeting = greetingService.getGreeting(person)
            call.respond(greeting)
        }
    }
}