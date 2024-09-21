package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.UserSession
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.OAuthAccessTokenResponse
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set

fun Route.authRouting(context: ApplicationContext) {
    with(context) {
        authenticate(properties.security.oauth2.name) {
            get("/login") {
                // Redirects for authentication
            }
            get("/callback") {
                val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
                if (principal != null) {
                    call.sessions.set(UserSession(principal.accessToken))
                    val redirect = principal.state?.let { requestCache.get(it) } ?: "/"
                    call.respondRedirect(redirect)
                } else {
                    call.respond(HttpStatusCode.Unauthorized)
                }
            }
        }
    }
}