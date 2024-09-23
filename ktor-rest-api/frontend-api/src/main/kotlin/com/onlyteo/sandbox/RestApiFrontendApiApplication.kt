package com.onlyteo.sandbox

import com.onlyteo.sandbox.config.loadProperties
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.plugin.*
import com.onlyteo.sandbox.properties.KtorPropertiesHolder
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    val ktorProperties = loadProperties<KtorPropertiesHolder>().ktor

    with(ktorProperties.deployment) {
        embeddedServer(
            Netty,
            port = port,
            host = host,
            module = Application::module,
        ).start(wait = true)
    }
}

fun Application.module() {
    val context = ApplicationContext()

    configureSerialization()
    configureValidation()
    configureLogging()
    configureWebjars()
    configureErrorHandling()
    configureRouting(context)
}