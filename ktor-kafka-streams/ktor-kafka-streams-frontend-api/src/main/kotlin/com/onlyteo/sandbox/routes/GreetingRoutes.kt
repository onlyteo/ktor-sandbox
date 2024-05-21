package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.config.Config
import com.onlyteo.sandbox.service.GreetingService
import io.ktor.client.HttpClient
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.greetingRouting(config: Config, httpClient: HttpClient) {

    val greetingService = GreetingService(config.app.integrations.greetingService, httpClient)

    authenticate("spring-authorization-server") {
        get("/api/greeting") {
            val name = call.parameters["name"]
            val greeting = greetingService.getGreeting(name)
            call.respond(greeting)
        }
    }
}