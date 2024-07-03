package com.onlyteo.sandbox

import com.onlyteo.sandbox.config.ExceptionHandler
import com.onlyteo.sandbox.config.hikariDataSource
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
import com.onlyteo.sandbox.repository.GreetingRepository
import com.onlyteo.sandbox.repository.PersonRepository
import com.onlyteo.sandbox.repository.PrefixRepository
import com.onlyteo.sandbox.service.GreetingService
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import org.jooq.SQLDialect
import org.jooq.impl.DSL

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
        val dataSource = hikariDataSource()
        val context = DSL.using(dataSource, SQLDialect.POSTGRES)
        val prefixRepository = PrefixRepository(properties.resources.prefixesFile)
        val personRepository = PersonRepository(context)
        val greetingRepository = GreetingRepository(context)
        val greetingService = GreetingService(prefixRepository, personRepository, greetingRepository)

        configureSerialization()
        configureValidation()
        configureLogging()
        configureWebjars()
        configureErrorHandling(exceptionHandler)
        configureRouting(greetingService)
        configureDatabase(dataSource)
    }
}