package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.FormData
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
                model = mapOf(
                    "formData" to FormData(null)
                )
            )
        )
    }

    post("/") {
        val parameters = call.receiveParameters()
        val name = requireNotNull(parameters["name"]) { "Name is required" }
        val greeting = greetingService.getGreeting(Person(name))
        call.respond(
            ThymeleafContent(
                template = "index",
                model = mapOf(
                    "formData" to FormData(null),
                    "greeting" to greeting
                )
            )
        )
    }
}