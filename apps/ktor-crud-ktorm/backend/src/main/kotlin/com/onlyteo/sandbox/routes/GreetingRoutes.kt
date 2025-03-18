package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.Person
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Route.greetingRoutes(applicationContext: ApplicationContext) {
    with(applicationContext) {

        get("/api/greetings") {
            val name = requireNotNull(call.parameters["name"]) { "Missing name parameter" }
            val greetings = greetingService.findGreetings(name)
            call.respond(greetings)
        }

        post<Person>("/api/greetings") { person ->
            val greeting = greetingService.getGreeting(person)
            call.respond(greeting)
        }
    }
}