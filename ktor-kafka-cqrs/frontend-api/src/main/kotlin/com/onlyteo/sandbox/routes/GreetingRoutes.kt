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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.concurrent.atomic.AtomicBoolean


context(ApplicationContext)
fun Route.greetingRouting(
    personKafkaProducer: KafkaProducer<String, Person>,
    greetingChannel: Channel<Greeting>
) {
    val producerProperties = properties.kafka.producer
    val logger = buildLogger
    val semaphore = AtomicBoolean(true)

    post("/api/greetings") {
        val person = call.receive<Person>()
        logger.info("Sending person \"{}\" on topic \"{}\"", person.name, producerProperties.targetTopic)
        personKafkaProducer.send(ProducerRecord(producerProperties.targetTopic, person.name, person))
        call.response.status(HttpStatusCode.Accepted)
    }

    webSocket("/ws/greetings") {
        try {
            logger.info("Opening websocket session")
            while (semaphore.get()) {
                greetingChannel.consumeEach { greeting ->
                    logger.info("Sending greeting \"{}\" to websocket \"/ws/greetings\"", greeting.message)
                    sendSerialized(greeting)
                }
                delay(1000)
            }
        } catch (ex: Exception) {
            logger.error("Error while processing greeting", ex)
        } finally {
            logger.info("Closing websocket session")
        }
    }
}