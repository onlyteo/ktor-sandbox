package com.onlyteo.sandbox.app.routes

import com.onlyteo.sandbox.app.context.ApplicationContext
import com.onlyteo.sandbox.lib.logging.factory.buildSecurityLogger
import com.onlyteo.sandbox.app.model.UserSession
import com.onlyteo.sandbox.app.model.asUserSession
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
                        com.onlyteo.sandbox.app.routes.securityLogger.debug("Login success. Initializing user session")
                        call.sessions.set(principal.asUserSession())
                        val redirect = principal.state?.let { requestCache.get(it) } ?: "/"
                        call.respondRedirect(redirect)
                    } else {
                        com.onlyteo.sandbox.app.routes.securityLogger.debug("Login failed")
                        call.respond(HttpStatusCode.Unauthorized)
                    }
                }
            }
        }
    }
}