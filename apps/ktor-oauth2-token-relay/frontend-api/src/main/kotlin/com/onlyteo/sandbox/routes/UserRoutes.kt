package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.exception.HttpStatusException
import com.onlyteo.sandbox.model.User
import com.onlyteo.sandbox.model.UserSession
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions

fun Route.userRoutes(applicationContext: ApplicationContext) {
    with(applicationContext) {
        with(properties.security) {
            authenticate(session.name, oauth2.name) {
                get("/api/user") {
                    val session = call.sessions.get<UserSession>() ?: throw HttpStatusException(
                        HttpStatusCode.Forbidden,
                        "No session found"
                    )
                    call.respond(User(session.accessToken.subject))
                }
            }
        }
    }
}