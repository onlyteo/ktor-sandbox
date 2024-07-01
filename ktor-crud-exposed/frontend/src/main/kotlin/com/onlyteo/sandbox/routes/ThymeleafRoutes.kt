package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.service.GreetingService
import io.ktor.server.application.call
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.thymeleaf.ThymeleafContent

context(ApplicationContext)
fun Route.thymeleafRoutes(greetingService: GreetingService) {

    get("/") {
        call.respond(
            ThymeleafContent(
                template = "index",
                model = mapOf()
            )
        )
    }

    post("/") {
        val parameters = call.receiveParameters()
        val name = requireNotNull(parameters["name"]) { "Name is required" }
        val history = parameters["history"].let { it == "on" }
        val greeting = greetingService.getGreeting(Person(name))
        val greetings = if (history) greetingService.getGreetings(name) else emptyList()
        call.respond(
            ThymeleafContent(
                template = "index",
                model = mapOf(
                    "history" to history,
                    "greeting" to greeting,
                    "greetings" to greetings
                )
            )
        )
    }
}