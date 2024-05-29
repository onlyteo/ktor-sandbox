package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.cache.RequestCache
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.UserSession
import io.ktor.server.application.call
import io.ktor.server.auth.OAuthAccessTokenResponse
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set

context(ApplicationContext)
fun Route.authRouting(requestCache: RequestCache<String, String>) {
    authenticate("spring-authorization-server") {
        get("/login") {
            // Redirects for authentication
        }
        get("/callback") {
            val currentPrincipal: OAuthAccessTokenResponse.OAuth2? = call.principal()
            currentPrincipal?.let { principal ->
                principal.state?.let { state ->
                    call.sessions.set(UserSession(state, principal.accessToken))
                    requestCache.get(state)?.let { redirect ->
                        call.respondRedirect(redirect)
                        return@get
                    }
                }
            }
            call.respondRedirect("/")
        }
    }
}