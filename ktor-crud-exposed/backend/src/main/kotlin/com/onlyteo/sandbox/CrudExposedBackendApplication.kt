package com.onlyteo.sandbox

import com.onlyteo.sandbox.config.ExceptionHandler
import com.onlyteo.sandbox.config.loadProperties
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.plugin.configureDatabase
import com.onlyteo.sandbox.plugin.configureErrorHandling
import com.onlyteo.sandbox.plugin.configureLogging
import com.onlyteo.sandbox.plugin.configureRouting
import com.onlyteo.sandbox.plugin.configureSerialization
import com.onlyteo.sandbox.plugin.configureValidation
import com.onlyteo.sandbox.plugin.configureWebjars
import com.onlyteo.sandbox.properties.ApplicationPropertiesHolder
import com.onlyteo.sandbox.properties.KtorPropertiesHolder
import com.onlyteo.sandbox.reposiitory.GreetingRepository
import com.onlyteo.sandbox.reposiitory.PersonRepository
import com.onlyteo.sandbox.reposiitory.PrefixRepository
import com.onlyteo.sandbox.service.GreetingService
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
        val exceptionHandler = ExceptionHandler()
        val prefixRepository = PrefixRepository(properties.resources.prefixesFile)
        val personRepository = PersonRepository()
        val greetingRepository = GreetingRepository()
        val greetingService = GreetingService(prefixRepository, personRepository, greetingRepository)

        configureSerialization()
        configureValidation()
        configureLogging()
        configureWebjars()
        configureErrorHandling(exceptionHandler)
        configureRouting(greetingService)
        configureDatabase()
    }
}