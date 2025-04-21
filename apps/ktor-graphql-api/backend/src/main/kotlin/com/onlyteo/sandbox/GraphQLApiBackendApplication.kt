package com.onlyteo.sandbox

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.errors.plugin.configureGraphQLErrorHandling
import com.onlyteo.sandbox.logging.plugin.configureLogging
import com.onlyteo.sandbox.plugin.configureGraphQL
import com.onlyteo.sandbox.plugin.configureRouting
import com.onlyteo.sandbox.plugin.configureWebjars
import com.onlyteo.sandbox.properties.KtorPropertiesHolder
import com.onlyteo.sandbox.properties.loadProperties
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer

fun main() {
    val ktorProperties = loadProperties<KtorPropertiesHolder>().ktor

    with(ktorProperties.deployment) {
        embeddedServer(
            io.ktor.server.netty.Netty,
            port = port,
            host = host,
            module = Application::module,
        ).start(wait = true)
    }
}

fun Application.module() {
    val applicationContext = ApplicationContext()

    configureLogging()
    configureWebjars()
    configureGraphQLErrorHandling()
    configureGraphQL(applicationContext)
    configureRouting()
}