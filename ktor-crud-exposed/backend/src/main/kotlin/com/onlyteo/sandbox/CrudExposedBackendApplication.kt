package com.onlyteo.sandbox

import com.onlyteo.sandbox.config.loadProperties
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.plugin.configureDatabase
import com.onlyteo.sandbox.plugin.configureRouting
import com.onlyteo.sandbox.plugin.configureSerialization
import com.onlyteo.sandbox.plugin.configureWebjars
import com.onlyteo.sandbox.properties.ApplicationPropertiesHolder
import com.onlyteo.sandbox.properties.KtorPropertiesHolder
import com.onlyteo.sandbox.service.GreetingService
import com.onlyteo.sandbox.service.PrefixService
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
    val applicationProperties = loadProperties<ApplicationPropertiesHolder>().app

    with(ApplicationContext(applicationProperties)) {
        val prefixService = PrefixService(properties.resources.prefixesFile)
        val greetingService = GreetingService(prefixService)

        configureSerialization()
        configureWebjars()
        configureRouting(greetingService)
        configureDatabase()
    }
}