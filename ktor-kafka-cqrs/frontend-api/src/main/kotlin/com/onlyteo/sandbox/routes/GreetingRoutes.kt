package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.config.buildLogger
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.websocket.sendSerialized
import io.ktor.server.websocket.webSocket
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.time.Duration

context(ApplicationContext)
fun Route.greetingRouting(
    personKafkaProducer: KafkaProducer<String, Person>,
    greetingKafkaConsumer: KafkaConsumer<String, Greeting>
) {
    val producerProperties = properties.kafka.producer
    val consumerProperties = properties.kafka.consumer
    val logger = buildLogger

    post("/api/greetings") {
        val person = call.receive<Person>()
        logger.info("Sending person \"{}\" on topic \"{}\"", person.name, producerProperties.targetTopic)
        personKafkaProducer.send(ProducerRecord(producerProperties.targetTopic, person.name, person))
        call.response.status(HttpStatusCode.Accepted)
    }

    webSocket("/ws/greetings") {
        logger.info("WebSocket session subscribing to topic \"{}\"", consumerProperties.sourceTopic)
        try {
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
                        logger.info("Sending greeting \"{}\" to websocket \"/ws/greetings\"", greeting.message)
                        sendSerialized(greeting)
                    }
            }
        } finally {
            logger.info("Kafka consumer finished polling")
        }
    }
}