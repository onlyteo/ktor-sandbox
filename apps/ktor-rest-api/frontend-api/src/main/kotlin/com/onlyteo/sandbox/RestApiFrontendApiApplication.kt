package com.onlyteo.sandbox

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.errors.plugin.configureRestErrorHandling
import com.onlyteo.sandbox.logging.plugin.configureLogging
import com.onlyteo.sandbox.plugin.configureRouting
import com.onlyteo.sandbox.plugin.configureValidation
import com.onlyteo.sandbox.plugin.configureWebjars
import com.onlyteo.sandbox.properties.KtorPropertiesHolder
import com.onlyteo.sandbox.properties.loadProperties
import com.onlyteo.sandbox.serialization.plugin.configureSerialization
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

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
    val applicationContext = ApplicationContext()

    configureSerialization()
    configureValidation()
    configureLogging()
    configureWebjars()
    configureRestErrorHandling()
    configureRouting(applicationContext)
}