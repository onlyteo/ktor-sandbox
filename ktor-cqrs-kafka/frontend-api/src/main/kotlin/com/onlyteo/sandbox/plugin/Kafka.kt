package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.config.buildApplicationLogger
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.plugin.custom.KafkaConsumerPlugin
import com.onlyteo.sandbox.plugin.custom.KafkaProducerPlugin
import io.ktor.server.application.Application
import io.ktor.server.application.install
import kotlinx.coroutines.runBlocking

fun Application.configureKafka(context: ApplicationContext) {
    val sourceTopic = context.properties.kafka.consumer.sourceTopic
    val greetingKafkaConsumer = context.greetingKafkaConsumer
    val personKafkaProducer = context.personKafkaProducer
    val greetingChannel = context.greetingChannel
    val logger = buildApplicationLogger

    install(KafkaProducerPlugin) {
        kafkaProducer = personKafkaProducer
    }
    install(KafkaConsumerPlugin) {
        useCoroutines<String, Greeting> {
            kafkaTopics = listOf(sourceTopic)
            kafkaConsumer = greetingKafkaConsumer
            consumeFunction = { records ->
                runBlocking {
                    records
                        .map { it.value() }
                        .forEach { greeting ->
                            logger.info("Received greeting \"{}\" on topic \"{}\"", greeting.message, sourceTopic)
                            greetingChannel.send(greeting)
                        }
                }
            }
            errorFunction = { throwable ->
                logger.error("An error occurred while consuming greetings on topic \"${sourceTopic}\"", throwable)
                throw throwable
            }
        }
    }
}