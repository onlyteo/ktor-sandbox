package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.handler.KafkaStreamsExceptionHandler
import com.onlyteo.sandbox.plugin.custom.KafkaStreamsPlugin
import com.onlyteo.sandbox.topology.buildKafkaTopology
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.configureKafka(context: ApplicationContext) {
    install(KafkaStreamsPlugin) {
        streamsProperties = context.properties.kafka.streams.asMap()
        streamsTopology = buildKafkaTopology(context)
        streamsExceptionHandler = KafkaStreamsExceptionHandler()
    }
}