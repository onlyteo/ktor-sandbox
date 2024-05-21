package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.service.GreetingService
import io.ktor.server.application.call
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.greetingRouting(config: ApplicationConfig) {

    val url = config.propertyOrNull("app.greeting-service.url")?.getString()
        ?: throw IllegalStateException("Property \"app.greeting-service.url\" is missing")
    val greetingService = GreetingService(url)

    get("/api/greeting") {
        val name = call.parameters["name"]
        val greeting = greetingService.getGreeting(name)
        call.respond(greeting)
    }
}