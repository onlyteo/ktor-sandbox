package com.onlyteo.sandbox.plugin

import com.auth0.jwt.JWT
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.UserSession
import com.onlyteo.sandbox.model.asAccessToken
import com.onlyteo.sandbox.serializer.JacksonSessionSerializer
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.OAuthServerSettings
import io.ktor.server.auth.oauth
import io.ktor.server.auth.session
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import no.onlyteo.sandbox.logging.factory.buildSecurityLogger
import java.time.Instant

private val securityLogger = buildSecurityLogger

fun Application.configAuthentication(applicationContext: ApplicationContext) {
    with(applicationContext) {
        with(properties.security) {
            install(Sessions) {
                cookie<UserSession>(session.cookieName) {
                    serializer = JacksonSessionSerializer()
                }
            }
            install(Authentication) {
                session<UserSession>(session.name) {
                    validate { currentSession ->
                        val sessionExpiry = Instant.ofEpochMilli(currentSession.expiresAt)
                        val accessTokenExpiry = Instant.ofEpochMilli(currentSession.accessToken.expiresAt)
                        if (Instant.now().isAfter(sessionExpiry)) {
                            securityLogger.debug("Session expired")
                            null
                        } else if (Instant.now().isAfter(accessTokenExpiry)) {
                            securityLogger.debug("Access token expired")
                            val oauth2TokenResponse = authService.getOAuth2Token(currentSession.refreshToken)
                            val (accessToken, refreshToken, _, expiresIn) = oauth2TokenResponse
                            val accessTokenObject = JWT.decode(accessToken).asAccessToken()
                            val expiresAt = Instant.now().plusSeconds(expiresIn).toEpochMilli()
                            UserSession(accessTokenObject, refreshToken, expiresAt)
                        } else {
                            securityLogger.debug("Session verified")
                            currentSession
                        }
                    }
                    challenge {
                        call.response.header(HttpHeaders.Location, "/login")
                        call.respond(HttpStatusCode.Unauthorized)
                    }
                }
                oauth(oauth2.name) {
                    client = httpClient
                    providerLookup = {
                        OAuthServerSettings.OAuth2ServerSettings(
                            name = oauth2.provider.name,
                            authorizeUrl = oauth2.provider.authorizeUrl,
                            accessTokenUrl = oauth2.provider.tokenUrl,
                            requestMethod = HttpMethod.Post,
                            accessTokenRequiresBasicAuth = true,
                            clientId = oauth2.provider.clientId,
                            clientSecret = oauth2.provider.clientSecret,
                            defaultScopes = oauth2.provider.scopes,
                            onStateCreated = { call, state ->
                                val redirect = referrerAwareRedirectResolver()
                                requestCache.put(state, redirect)
                            }
                        )
                    }
                    urlProvider = {
                        oauth2.callbackUrl
                    }
                }
            }
        }
    }
}

private fun ApplicationCall.referrerAwareRedirectResolver(): String {
    val redirect = request.queryParameters["redirectUrl"] ?: "/"
    return request.headers[HttpHeaders.Referrer] ?: redirect
}