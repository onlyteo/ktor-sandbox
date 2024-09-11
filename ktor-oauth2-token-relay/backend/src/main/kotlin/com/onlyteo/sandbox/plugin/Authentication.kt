package com.onlyteo.sandbox.plugin

import com.auth0.jwk.JwkProviderBuilder
import com.onlyteo.sandbox.properties.ApplicationProperties
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond
import java.net.URI

fun Application.configAuthentication(
    properties: ApplicationProperties,
) {
    install(Authentication) {
        with(properties.security.oauth2) {
            val jwkUrl = URI.create(provider.jwkUrl).toURL()
            val jwkProvider = JwkProviderBuilder(jwkUrl).build()

            jwt(name) {
                verifier(jwkProvider, provider.issuer)
                validate { credential ->
                    credential.payload.issuer?.let { JWTPrincipal(credential.payload) }
                }
                challenge { _, _ ->
                    call.respond(HttpStatusCode.Unauthorized)
                }
            }
        }
    }
}