package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.Person
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.opentelemetry.api.trace.Span

fun Route.greetingRoutes(context: ApplicationContext) {
    val greetingService = context.greetingService

    post("/api/greetings") {
        val person = call.receive<Person>()
        Span.current().setAttribute("person.name", person.name)
        val greeting = greetingService.getGreeting(person)
        call.respond(greeting)
    }
}