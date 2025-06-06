package com.onlyteo.sandbox.app.routes

import com.onlyteo.sandbox.app.context.ApplicationContext
import com.onlyteo.sandbox.app.model.Person
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Route.greetingRoutes(applicationContext: ApplicationContext) {
    with(applicationContext) {

        get("/api/greetings") {
            val name = call.parameters["name"] ?: throw BadRequestException("Missing name parameter")
            val greetings = greetingService.findGreetings(name)
            call.respond(greetings)
        }

        post<Person>("/api/greetings") { person ->
            val greeting = greetingService.getGreeting(person)
            call.respond(greeting)
        }
    }
}