package com.onlyteo.sandbox.app.plugin

import com.onlyteo.sandbox.app.context.ApplicationContext
import com.onlyteo.sandbox.lib.kafka.plugin.KafkaConsumerPlugin
import com.onlyteo.sandbox.lib.kafka.plugin.KafkaProducerPlugin
import com.onlyteo.sandbox.lib.logging.factory.buildApplicationLogger
import com.onlyteo.sandbox.app.model.Greeting
import io.ktor.server.application.Application
import io.ktor.server.application.install
import kotlinx.coroutines.runBlocking

fun Application.configureKafka(applicationContext: ApplicationContext) {
    with(applicationContext) {
        val logger = buildApplicationLogger
        val sourceTopic = properties.kafka.consumer.sourceTopic

        install(KafkaProducerPlugin) {
            kafkaProducer = personKafkaProducer
        }
        install(KafkaConsumerPlugin) {
            useCoroutines<String, Greeting> {
                topics = listOf(sourceTopic)
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
}