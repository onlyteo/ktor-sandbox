package com.onlyteo.sandbox.app.plugin

import com.onlyteo.sandbox.lib.serialization.factory.buildObjectMapper
import io.ktor.serialization.jackson.JacksonWebsocketContentConverter
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.websocket.WebSockets

fun Application.configureWebSockets() {
    install(WebSockets) {
        contentConverter = JacksonWebsocketContentConverter(buildObjectMapper)
    }
}