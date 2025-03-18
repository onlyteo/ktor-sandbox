package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.plugin.custom.KafkaStreamsPlugin
import com.onlyteo.sandbox.topology.KafkaStreamsExceptionHandler
import com.onlyteo.sandbox.topology.buildKafkaTopology
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.configureKafka(applicationContext: ApplicationContext) {
    with(applicationContext) {
        install(KafkaStreamsPlugin) {
            streamsProperties = properties.kafka.streams.asMap()
            streamsTopology = buildKafkaTopology(applicationContext)
            streamsExceptionHandler = KafkaStreamsExceptionHandler()
        }
    }
}