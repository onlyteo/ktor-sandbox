package com.onlyteo.sandbox.app.plugin

import com.auth0.jwk.JwkProviderBuilder
import com.onlyteo.sandbox.app.context.ApplicationContext
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond
import java.net.URI

fun Application.configAuthentication(applicationContext: ApplicationContext) {
    install(Authentication) {
        with(applicationContext.properties.security.oauth2) {
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