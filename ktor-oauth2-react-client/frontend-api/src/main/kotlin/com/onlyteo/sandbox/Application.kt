package com.onlyteo.sandbox

import io.ktor.server.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun main() {
    embeddedServer(
        factory = Netty,
        host = "0.0.0.0",
        port = 8080,
        module = {
            routing {
                get("/") {
                    call.respondText("Hello World!")
                }
            }
        }
    ).start(wait = true)
}
