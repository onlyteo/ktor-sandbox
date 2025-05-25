package com.onlyteo.sandbox.app

import com.onlyteo.sandbox.app.context.ApplicationContext
import com.onlyteo.sandbox.lib.errors.plugin.configureRestErrorHandling
import com.onlyteo.sandbox.lib.logging.plugin.configureLogging
import com.onlyteo.sandbox.app.plugin.configureRouting
import com.onlyteo.sandbox.app.plugin.configureThymeleaf
import com.onlyteo.sandbox.app.plugin.configureWebjars
import com.onlyteo.sandbox.properties.KtorPropertiesHolder
import com.onlyteo.sandbox.properties.loadProperties
import com.onlyteo.sandbox.lib.serialization.plugin.configureSerialization
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
    configureLogging()
    configureWebjars()
    configureThymeleaf()
    configureRestErrorHandling()
    configureRouting(applicationContext)
}