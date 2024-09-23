package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.Person
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.greetingRoutes(context: ApplicationContext) {
    val greetingService = context.greetingService

    post("/api/greetings") {
        val person = call.receive<Person>()
        val greeting = greetingService.getGreeting(person)
        call.respond(greeting)
    }
}