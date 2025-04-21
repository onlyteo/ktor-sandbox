package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.logging.factory.buildSecurityLogger
import com.onlyteo.sandbox.model.UserSession
import com.onlyteo.sandbox.model.asUserSession
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.OAuthAccessTokenResponse
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.sessions.clear
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set

private val securityLogger = buildSecurityLogger

fun Route.authRouting(applicationContext: ApplicationContext) {
    with(applicationContext) {
        with(properties.security) {
            authenticate(oauth2.name) {
                get("/login") {
                    // Redirects for authentication
                }
                get("/logout") {
                    call.sessions.clear<UserSession>()
                }
                get("/callback") {
                    val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
                    if (principal != null) {
                        securityLogger.debug("Login success. Initializing user session")
                        call.sessions.set(principal.asUserSession())
                        val redirect = principal.state?.let { requestCache.get(it) } ?: "/"
                        call.respondRedirect(redirect)
                    } else {
                        securityLogger.debug("Login failed")
                        call.respond(HttpStatusCode.Unauthorized)
                    }
                }
            }
        }
    }
}