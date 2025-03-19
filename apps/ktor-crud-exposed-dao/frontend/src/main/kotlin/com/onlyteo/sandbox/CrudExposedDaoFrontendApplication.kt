package com.onlyteo.sandbox

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.plugin.configureErrorHandling
import com.onlyteo.sandbox.plugin.configureLogging
import com.onlyteo.sandbox.plugin.configureRouting
import com.onlyteo.sandbox.plugin.configureSerialization
import com.onlyteo.sandbox.plugin.configureThymeleaf
import com.onlyteo.sandbox.plugin.configureWebjars
import com.onlyteo.sandbox.properties.KtorPropertiesHolder
import com.onlyteo.sandbox.properties.loadProperties
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
    configureErrorHandling()
    configureRouting(applicationContext)
}