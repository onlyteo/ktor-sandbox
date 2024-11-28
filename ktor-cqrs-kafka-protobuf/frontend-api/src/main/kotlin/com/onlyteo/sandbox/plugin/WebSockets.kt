package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.config.buildObjectMapper
import io.ktor.serialization.jackson.JacksonWebsocketContentConverter
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.websocket.WebSockets

fun Application.configureWebSockets() {
    install(WebSockets) {
        contentConverter = JacksonWebsocketContentConverter(buildObjectMapper)
    }
}