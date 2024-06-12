package com.onlyteo.sandbox.config

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.jackson.jackson

fun buildHttpClient() = HttpClient(CIO) {
    install(ContentNegotiation) {
        jackson {
            configureJackson()
        }
    }
}