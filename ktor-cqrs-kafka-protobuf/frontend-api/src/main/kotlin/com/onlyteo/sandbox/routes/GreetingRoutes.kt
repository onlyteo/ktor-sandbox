package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.config.buildLogger
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.Person
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.websocket.sendSerialized
import io.ktor.server.websocket.webSocket
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.concurrent.atomic.AtomicBoolean


fun Route.greetingRouting(context: ApplicationContext) {
    val producerProperties = context.properties.kafka.producer
    val personKafkaProducer = context.personKafkaProducer
    val greetingChannel = context.greetingChannel
    val logger = buildLogger
    val websocketsRunning = AtomicBoolean(true)

    post("/api/greetings") {
        val person = call.receive<Person>()
        logger.info("Sending person \"{}\" on topic \"{}\"", person.name, producerProperties.targetTopic)
        personKafkaProducer.send(ProducerRecord(producerProperties.targetTopic, person.name, person))
        call.response.status(HttpStatusCode.Accepted)
    }

    webSocket("/ws/greetings") {
        try {
            logger.info("Opening websocket session")
            while (websocketsRunning.get()) {
                greetingChannel.consumeEach { greeting ->
                    logger.info("Sending greeting \"{}\" to websocket \"/ws/greetings\"", greeting.message)
                    sendSerialized(greeting)
                }
                delay(500)
            }
        } catch (ex: Exception) {
            logger.error("Error while processing greeting", ex)
        } finally {
            logger.info("Closing websocket session")
        }
    }
}