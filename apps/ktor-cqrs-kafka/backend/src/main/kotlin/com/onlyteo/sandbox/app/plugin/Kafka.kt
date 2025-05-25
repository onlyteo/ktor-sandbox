package com.onlyteo.sandbox.app.plugin

import com.onlyteo.sandbox.app.context.ApplicationContext
import com.onlyteo.sandbox.lib.kafka.handler.TerminatingKafkaStreamsExceptionHandler
import com.onlyteo.sandbox.lib.kafka.plugin.KafkaStreamsPlugin
import com.onlyteo.sandbox.app.topology.buildKafkaTopology
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.configureKafka(applicationContext: ApplicationContext) {
    install(KafkaStreamsPlugin) {
        properties = applicationContext.properties.kafka.streams
        kafkaTopology = buildKafkaTopology(applicationContext)
        exceptionHandler = TerminatingKafkaStreamsExceptionHandler()
    }
}