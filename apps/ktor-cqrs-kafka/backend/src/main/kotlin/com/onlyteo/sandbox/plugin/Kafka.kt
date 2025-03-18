package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.kafka.handler.TerminatingKafkaStreamsExceptionHandler
import com.onlyteo.sandbox.kafka.plugin.KafkaStreamsPlugin
import com.onlyteo.sandbox.topology.buildKafkaTopology
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.configureKafka(applicationContext: ApplicationContext) {
    with(applicationContext) {
        install(KafkaStreamsPlugin) {
            streamsProperties = properties.kafka.streams
            streamsTopology = buildKafkaTopology(applicationContext)
            streamsExceptionHandler = TerminatingKafkaStreamsExceptionHandler()
        }
    }
}