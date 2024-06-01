package com.onlyteo.sandbox

import com.onlyteo.sandbox.config.loadProperties
import com.onlyteo.sandbox.plugin.configureRouting
import com.onlyteo.sandbox.plugin.configureSerialization
import com.onlyteo.sandbox.plugin.configureWebjars
import com.onlyteo.sandbox.properties.KTOR_PROPERTIES_FILE
import com.onlyteo.sandbox.properties.KtorPropertiesHolder
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer

fun main() {
    val propertiesHolder = loadProperties<KtorPropertiesHolder>(KTOR_PROPERTIES_FILE)

    with(propertiesHolder.ktor.deployment) {
        embeddedServer(
            io.ktor.server.netty.Netty,
            port = port,
            host = host,
            module = io.ktor.server.application.Application::module,
        ).start(wait = true)
    }
}

fun Application.module() {
    configureSerialization()
    configureWebjars()
    configureRouting()
}