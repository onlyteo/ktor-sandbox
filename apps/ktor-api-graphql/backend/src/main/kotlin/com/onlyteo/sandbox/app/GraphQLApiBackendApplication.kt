package com.onlyteo.sandbox.app

import com.onlyteo.sandbox.app.context.ApplicationContext
import com.onlyteo.sandbox.lib.errors.plugin.configureGraphQLErrorHandling
import com.onlyteo.sandbox.lib.logging.plugin.configureLogging
import com.onlyteo.sandbox.app.plugin.configureGraphQL
import com.onlyteo.sandbox.app.plugin.configureRouting
import com.onlyteo.sandbox.app.plugin.configureWebjars
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