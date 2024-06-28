package com.onlyteo.sandbox

import com.onlyteo.sandbox.config.buildRestClient
import com.onlyteo.sandbox.config.loadProperties
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.plugin.configureLogging
import com.onlyteo.sandbox.plugin.configureRouting
import com.onlyteo.sandbox.plugin.configureSerialization
import com.onlyteo.sandbox.plugin.configureThymeleaf
import com.onlyteo.sandbox.plugin.configureWebjars
import com.onlyteo.sandbox.properties.ApplicationPropertiesHolder
import com.onlyteo.sandbox.properties.KtorPropertiesHolder
import com.onlyteo.sandbox.service.GreetingService
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
    val applicationProperties = loadProperties<ApplicationPropertiesHolder>().app

    with(ApplicationContext(applicationProperties)) {
        val httpClient = buildRestClient()
        val greetingService = GreetingService(httpClient)

        configureSerialization()
        configureLogging()
        configureWebjars()
        configureThymeleaf()
        configureRouting(greetingService)
    }
}