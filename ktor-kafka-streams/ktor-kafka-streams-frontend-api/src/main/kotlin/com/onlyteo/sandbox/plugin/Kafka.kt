package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.config.buildStreamsTopology
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.plugin.kafka.KafkaStreamsPlugin
import io.ktor.server.application.Application
import io.ktor.server.application.install

context(ApplicationContext)
fun Application.configureKafka() {
    install(KafkaStreamsPlugin) {
        properties = applicationProperties.kafka.streams.asMap()
        topology = buildStreamsTopology()
    }
}