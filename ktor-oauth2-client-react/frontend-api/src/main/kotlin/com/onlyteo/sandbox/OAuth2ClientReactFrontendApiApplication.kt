package com.onlyteo.sandbox

import com.onlyteo.sandbox.config.buildRequestCache
import com.onlyteo.sandbox.config.buildRestClient
import com.onlyteo.sandbox.config.loadProperties
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.plugin.configAuthentication
import com.onlyteo.sandbox.plugin.configureRouting
import com.onlyteo.sandbox.plugin.configureSerialization
import com.onlyteo.sandbox.plugin.configureWebjars
import com.onlyteo.sandbox.properties.KTOR_PROPERTIES_FILE
import com.onlyteo.sandbox.properties.KtorPropertiesHolder
import com.onlyteo.sandbox.service.GreetingService
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    val propertiesHolder = loadProperties<KtorPropertiesHolder>(KTOR_PROPERTIES_FILE)

    with(propertiesHolder.ktor.deployment) {
        embeddedServer(
            Netty,
            port = port,
            host = host,
            module = Application::module,
        ).start(wait = true)
    }
}

fun Application.module() {
    with(ApplicationContext()) {
        val httpClient = buildRestClient()
        val requestCache = buildRequestCache()
        val greetingService = GreetingService()
        configureSerialization()
        configAuthentication(httpClient, requestCache)
        configureRouting(requestCache, greetingService)
        configureWebjars()
    }
}