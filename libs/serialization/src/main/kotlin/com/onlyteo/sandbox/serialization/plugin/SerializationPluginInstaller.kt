package com.onlyteo.sandbox.serialization.plugin

import com.onlyteo.sandbox.serialization.factory.configureJackson
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        jackson {
            configureJackson()
        }
    }
}