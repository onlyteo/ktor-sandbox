package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.topology.KafkaStreamsExceptionHandler
import com.onlyteo.sandbox.topology.buildKafkaTopology
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.plugin.kafka.KafkaStreamsPlugin
import com.onlyteo.sandbox.service.GreetingService
import io.ktor.server.application.Application
import io.ktor.server.application.install

context(ApplicationContext)
fun Application.configureKafka(greetingService: GreetingService) {
    install(KafkaStreamsPlugin) {
        streamsProperties = properties.kafka.streams.asMap()
        streamsTopology = buildKafkaTopology(greetingService)
        streamsExceptionHandler = KafkaStreamsExceptionHandler()
    }
}