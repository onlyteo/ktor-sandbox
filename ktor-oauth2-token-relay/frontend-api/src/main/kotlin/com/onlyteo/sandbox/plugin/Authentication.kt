package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.cache.RequestCache
import com.onlyteo.sandbox.context.ApplicationContext
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

context(ApplicationContext)
fun Application.configAuthentication(
    httpClient: HttpClient,
    requestCache: RequestCache<String, String>
) {
    install(Sessions) {
        cookie<UserSession>("USER_SESSION")
    }
    install(Authentication) {
        with(properties.security.oauth2) {
            oauth(name) {
                client = httpClient
                providerLookup = {
                    OAuthServerSettings.OAuth2ServerSettings(
                        name = provider.name,
                        authorizeUrl = provider.authorizeUrl,
                        accessTokenUrl = provider.tokenUrl,
                        requestMethod = HttpMethod.Post,
                        accessTokenRequiresBasicAuth = true,
                        clientId = provider.clientId,
                        clientSecret = provider.clientSecret,
                        defaultScopes = provider.scopes,
                        onStateCreated = { call, state ->
                            call.request.queryParameters["redirectUrl"]?.let {
                                requestCache.put(state, it)
                            }
                        }
                    )
                }
                urlProvider = {
                    callbackUrl
                }
            }
        }
    }
}