package com.onlyteo.sandbox

import com.onlyteo.sandbox.config.buildKafkaProducer
import com.onlyteo.sandbox.config.loadProperties
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.plugin.configureKafka
import com.onlyteo.sandbox.plugin.configureRouting
import com.onlyteo.sandbox.plugin.configureSerialization
import com.onlyteo.sandbox.plugin.configureWebjars
import com.onlyteo.sandbox.properties.ApplicationPropertiesHolder
import com.onlyteo.sandbox.service.GreetingService
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain
import org.slf4j.LoggerFactory

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    val logger = LoggerFactory.getLogger("com.onlyteo.sandbox.application")
    val propertiesHolder = loadProperties<ApplicationPropertiesHolder>("/application.yaml")
    val kafkaProducer = buildKafkaProducer(propertiesHolder.app.kafka.producer)
    val greetingService = GreetingService(propertiesHolder.app.kafka.producer, kafkaProducer)

    ApplicationContext(logger, propertiesHolder.app).apply {
        configureSerialization()
        configureWebjars()
        configureRouting(greetingService)
        configureKafka()
    }
}