package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.config.buildLogger
import com.onlyteo.sandbox.context.ApplicationContext
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.application.ApplicationStopping
import kotlinx.coroutines.launch
import java.time.Duration

fun Application.configureKafka(context: ApplicationContext) {
    val consumerProperties = context.properties.kafka.consumer
    val greetingKafkaConsumer = context.greetingKafkaConsumer
    val personKafkaProducer = context.personKafkaProducer
    val greetingChannel = context.greetingChannel
    val logger = buildLogger

    environment.monitor.subscribe(ApplicationStarted) {
        launch {
            try {
                logger.info("Kafka consumer starting subscription on topic {}", consumerProperties.sourceTopic)
                greetingKafkaConsumer.subscribe(listOf(consumerProperties.sourceTopic))
                while (true) {
                    greetingKafkaConsumer.poll(Duration.ofMillis(500))
                        .forEach { record ->
                            val greeting = record.value()
                            logger.info(
                                "Received greeting \"{}\" on topic \"{}\"",
                                greeting.message,
                                consumerProperties.sourceTopic
                            )
                            greetingChannel.send(greeting)
                        }
                }
            } finally {
                logger.info("Kafka consumer finished subscription on topic {}", consumerProperties.sourceTopic)
            }
        }
    }

    environment.monitor.subscribe(ApplicationStopping) {
        personKafkaProducer.close(Duration.ofSeconds(2))
        greetingKafkaConsumer.close(Duration.ofSeconds(2))
    }
}