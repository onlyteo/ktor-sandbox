package com.onlyteo.sandbox

import com.onlyteo.sandbox.config.ExceptionHandler
import com.onlyteo.sandbox.config.loadProperties
import com.onlyteo.sandbox.plugin.configAuthentication
import com.onlyteo.sandbox.plugin.configureErrorHandling
import com.onlyteo.sandbox.plugin.configureLogging
import com.onlyteo.sandbox.plugin.configureRouting
import com.onlyteo.sandbox.plugin.configureSerialization
import com.onlyteo.sandbox.plugin.configureValidation
import com.onlyteo.sandbox.plugin.configureWebjars
import com.onlyteo.sandbox.properties.ApplicationPropertiesHolder
import com.onlyteo.sandbox.properties.KtorPropertiesHolder
import com.onlyteo.sandbox.repository.PrefixRepository
import com.onlyteo.sandbox.service.GreetingService
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer

fun main() {
    val properties = loadProperties<KtorPropertiesHolder>().ktor

    with(properties.deployment) {
        embeddedServer(
            io.ktor.server.netty.Netty,
            port = port,
            host = host,
            module = Application::module,
        ).start(wait = true)
    }
}

fun Application.module() {
    val properties = loadProperties<ApplicationPropertiesHolder>().app

    val exceptionHandler = ExceptionHandler()
    val prefixRepository = PrefixRepository(properties.resources.prefixesFile)
    val greetingService = GreetingService(prefixRepository)

    configureSerialization()
    configureValidation()
    configureLogging()
    configureWebjars()
    configureErrorHandling(exceptionHandler)
    configAuthentication(properties)
    configureRouting(properties, greetingService)
}