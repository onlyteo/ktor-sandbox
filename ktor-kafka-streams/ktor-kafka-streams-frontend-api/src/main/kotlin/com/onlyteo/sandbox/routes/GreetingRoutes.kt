package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.service.GreetingService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.greetingRouting(greetingService: GreetingService) {

    post("/api/greeting") {
        val person = call.receive<Person>()
        greetingService.publishPerson(person)
        call.response.status(HttpStatusCode.Accepted)
    }
}