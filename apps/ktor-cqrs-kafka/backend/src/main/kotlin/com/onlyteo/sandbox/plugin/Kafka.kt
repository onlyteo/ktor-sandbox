package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.kafka.handler.TerminatingKafkaStreamsExceptionHandler
import com.onlyteo.sandbox.kafka.plugin.KafkaStreamsPlugin
import com.onlyteo.sandbox.topology.buildKafkaTopology
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.configureKafka(applicationContext: ApplicationContext) {
    install(KafkaStreamsPlugin) {
        properties = applicationContext.properties.kafka.streams
        kafkaTopology = buildKafkaTopology(applicationContext)
        exceptionHandler = TerminatingKafkaStreamsExceptionHandler()
    }
}