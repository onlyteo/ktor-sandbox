package com.onlyteo.sandbox.app.routes

import com.onlyteo.sandbox.app.context.ApplicationContext
import com.onlyteo.sandbox.lib.logging.factory.buildLogger
import com.onlyteo.sandbox.app.model.Person
import io.ktor.http.HttpStatusCode
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.websocket.sendSerialized
import io.ktor.server.websocket.webSocket
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import java.util.concurrent.atomic.AtomicBoolean


fun Route.greetingRouting(applicationContext: ApplicationContext) {
    with(applicationContext) {
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
}