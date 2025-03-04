package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.config.buildLogger
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.Person
import io.ktor.http.HttpStatusCode
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.websocket.sendSerialized
import io.ktor.server.websocket.webSocket
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import java.util.concurrent.atomic.AtomicBoolean


fun Route.greetingRouting(context: ApplicationContext) {
    val greetingService = context.greetingService
    val greetingChannel = context.greetingChannel
    val logger = buildLogger
    val websocketsRunning = AtomicBoolean(true)

    post<Person>("/api/greetings") { person ->
        logger.info("Received person \"{}\" in REST API \"/api/greetings\"", person.name)
        greetingService.sendMessage(person)
        call.response.status(HttpStatusCode.Accepted)
    }

    webSocket("/ws/greetings") {
        try {
            logger.info("Opening WebSocket session")
            while (websocketsRunning.get()) {
                greetingChannel.consumeEach { greeting ->
                    logger.info("Sending greeting \"{}\" to WebSocket \"/ws/greetings\"", greeting.message)
                    sendSerialized(greeting)
                }
                delay(500)
            }
        } catch (ex: Exception) {
            logger.error("Error while processing greeting", ex)
        } finally {
            logger.info("Closing WebSocket session")
        }
    }
}