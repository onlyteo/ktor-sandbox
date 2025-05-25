package com.onlyteo.sandbox.app.routes

import com.onlyteo.sandbox.app.context.ApplicationContext
import com.onlyteo.sandbox.app.model.Person
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.thymeleaf.ThymeleafContent

fun Route.thymeleafRoutes(applicationContext: ApplicationContext) {
    with(applicationContext) {

        get("/") {
            call.respond(
                ThymeleafContent(
                    template = "index",
                    model = mapOf(
                        "error" to false
                    )
                )
            )
        }

        post("/") {
            val parameters = call.receiveParameters()
            val name = parameters["name"]
            if (name.isNullOrBlank()) {
                call.respond(
                    HttpStatusCode.BadRequest, ThymeleafContent(
                        template = "index",
                        model = mapOf(
                            "error" to true
                        )
                    )
                )
            } else {
                val history = parameters["history"].let { it == "on" }
                val greeting = greetingService.getGreeting(Person(name))
                val greetings = if (history) greetingService.getGreetings(name) else emptyList()
                call.respond(
                    ThymeleafContent(
                        template = "index",
                        model = mapOf(
                            "error" to false,
                            "history" to history,
                            "greeting" to greeting,
                            "greetings" to greetings
                        )
                    )
                )
            }
        }
    }
}