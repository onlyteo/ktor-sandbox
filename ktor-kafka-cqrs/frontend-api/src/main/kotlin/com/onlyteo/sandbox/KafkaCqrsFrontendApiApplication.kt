package com.onlyteo.sandbox

import com.onlyteo.sandbox.config.buildGreetingKafkaConsumer
import com.onlyteo.sandbox.config.buildPersonKafkaProducer
import com.onlyteo.sandbox.config.loadProperties
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.plugin.configureKafka
import com.onlyteo.sandbox.plugin.configureRouting
import com.onlyteo.sandbox.plugin.configureSerialization
import com.onlyteo.sandbox.plugin.configureWebSockets
import com.onlyteo.sandbox.plugin.configureWebjars
import com.onlyteo.sandbox.properties.KTOR_PROPERTIES_FILE
import com.onlyteo.sandbox.properties.KtorPropertiesHolder
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.channels.Channel

fun main() {
    val propertiesHolder = loadProperties<KtorPropertiesHolder>(KTOR_PROPERTIES_FILE)

    with(propertiesHolder.ktor.deployment) {
        embeddedServer(
            Netty,
            port = port,
            host = host,
            module = Application::module
        ).start(wait = true)
    }
}

fun Application.module() {
    with(ApplicationContext()) {
        val personKafkaProducer = buildPersonKafkaProducer()
        val greetingKafkaConsumer = buildGreetingKafkaConsumer()
        val greetingChannel = Channel<Greeting>()

        configureSerialization()
        configureWebjars()
        configureWebSockets()
        configureRouting(personKafkaProducer, greetingChannel)
        configureKafka(personKafkaProducer, greetingKafkaConsumer, greetingChannel)
    }
}