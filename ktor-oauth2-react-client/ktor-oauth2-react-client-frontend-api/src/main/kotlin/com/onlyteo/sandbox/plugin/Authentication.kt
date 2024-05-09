package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.model.UserSession
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.OAuthServerSettings
import io.ktor.server.auth.oauth
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import kotlin.collections.set

fun Application.configAuthentication(
    httpClient: HttpClient,
    redirects: MutableMap<String, String>
) {
    install(Sessions) {
        cookie<UserSession>("USER_SESSION")
    }
    install(Authentication) {
        oauth("spring-authorization-server") {
            client = httpClient
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "spring-authorization-server",
                    authorizeUrl = "http://localhost:8888/oauth2/authorize",
                    accessTokenUrl = "http://localhost:8888/oauth2/token",
                    requestMethod = HttpMethod.Post,
                    accessTokenRequiresBasicAuth = true,
                    clientId = "sandbox-oauth2-client",
                    clientSecret = "G4nd4lf",
                    defaultScopes = listOf("openid", "profile", "roles"),
                    onStateCreated = { call, state ->
                        call.request.queryParameters["redirectUrl"]?.let {
                            redirects[state] = it
                        }
                    }
                )
            }
            urlProvider = {
                "http://localhost:8080/callback"
            }
        }
    }
}