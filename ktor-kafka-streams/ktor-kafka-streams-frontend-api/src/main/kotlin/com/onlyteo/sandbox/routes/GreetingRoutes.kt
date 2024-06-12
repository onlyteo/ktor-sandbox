package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.context.LoggingContext
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

context(ApplicationContext, LoggingContext)
fun Route.greetingRouting(
    personKafkaProducer: KafkaProducer<String, Person>,
    greetingKafkaConsumer: KafkaConsumer<String, Greeting>
) {

    post("/api/greetings") {
        val person = call.receive<Person>()
        logger.info("Sending person \"${person.name}\" on topic \"${properties.kafka.producer.targetTopic}\"")
        personKafkaProducer.send(ProducerRecord(properties.kafka.producer.targetTopic, person.name, person))
        call.response.status(HttpStatusCode.Accepted)
    }

    webSocket("/ws/greetings") {
        logger.info("WebSocket session subscribing to topic \"${properties.kafka.consumer.sourceTopic}\"")
        try {
            greetingKafkaConsumer.subscribe(listOf(properties.kafka.consumer.sourceTopic))
            while (true) {
                greetingKafkaConsumer.poll(Duration.ofMillis(500))
                    .forEach { record ->
                        val greeting = record.value()
                        logger.info("Received greeting \"${greeting.message}\" on topic \"${properties.kafka.consumer.sourceTopic}\"")
                        logger.info("Sending greeting \"${greeting.message}\" to websocket \"/ws/greetings\"")
                        sendSerialized(greeting)
                    }
            }
        } finally {
            logger.info("Kafka consumer finished polling")
        }
    }
}