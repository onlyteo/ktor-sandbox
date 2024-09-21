package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.UserSession
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

fun Application.configAuthentication(context: ApplicationContext) {
    with(context) {
        install(Sessions) {
            cookie<UserSession>(properties.security.session.cookieName)
        }
        install(Authentication) {
            session<UserSession>(properties.security.session.name) {
                validate { session ->
                    session.token?.let { session } // TODO Handle session and token expiration
                }
                challenge {
                    call.response.header(HttpHeaders.Location, "/login")
                    call.respond(HttpStatusCode.Unauthorized)
                }
            }
            oauth(properties.security.oauth2.name) {
                client = httpClient
                providerLookup = {
                    OAuthServerSettings.OAuth2ServerSettings(
                        name = properties.security.oauth2.provider.name,
                        authorizeUrl = properties.security.oauth2.provider.authorizeUrl,
                        accessTokenUrl = properties.security.oauth2.provider.tokenUrl,
                        requestMethod = HttpMethod.Post,
                        accessTokenRequiresBasicAuth = true,
                        clientId = properties.security.oauth2.provider.clientId,
                        clientSecret = properties.security.oauth2.provider.clientSecret,
                        defaultScopes = properties.security.oauth2.provider.scopes,
                        onStateCreated = { call, state ->
                            val redirect = referrerAwareRedirectResolver()
                            requestCache.put(state, redirect)
                        }
                    )
                }
                urlProvider = {
                    properties.security.oauth2.callbackUrl
                }
            }
        }
    }
}

private fun ApplicationCall.referrerAwareRedirectResolver(): String {
    val redirect = request.queryParameters["redirectUrl"] ?: "/"
    return request.headers[HttpHeaders.Referrer] ?: redirect
}