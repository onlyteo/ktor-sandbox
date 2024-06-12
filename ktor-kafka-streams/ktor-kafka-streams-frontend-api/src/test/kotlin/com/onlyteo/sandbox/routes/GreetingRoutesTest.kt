package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.config.buildObjectMapper
import com.onlyteo.sandbox.config.configureJackson
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.context.LoggingContext
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.plugin.configureRouting
import com.onlyteo.sandbox.plugin.configureSerialization
import com.onlyteo.sandbox.plugin.configureWebSockets
import com.onlyteo.sandbox.plugin.configureWebjars
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.serialization.jackson.JacksonWebsocketContentConverter
import io.ktor.serialization.jackson.jackson
import io.ktor.server.testing.testApplication
import io.ktor.websocket.Frame
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.runBlocking
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.TopicPartition
import java.time.Duration

class GreetingRoutesTest : FreeSpec({

    with(ApplicationContext()) {
        with(LoggingContext()) {
            with(TestContext()) {

                "Test suite for WebSockets" - {
                    val personKafkaProducerMock = mockk<KafkaProducer<String, Person>>()
                    val greetingKafkaConsumerMock = mockk<KafkaConsumer<String, Greeting>>()

                    testApplication {
                        application {
                            configureSerialization()
                            configureWebjars()
                            configureWebSockets()
                            configureRouting(personKafkaProducerMock, greetingKafkaConsumerMock)
                        }

                        val webSocketClient = createClient {
                            install(ContentNegotiation) {
                                jackson {
                                    configureJackson()
                                }
                            }
                            install(WebSockets) {
                                contentConverter = JacksonWebsocketContentConverter(buildObjectMapper)
                            }
                        }

                        "Should send message to WebSocket" {
                            val record = ConsumerRecord("greetings", 1, 1, "John", Greeting("Hello John!"))
                            val records = listOf(record)
                            val recordsPerPartition = mapOf(TopicPartition("greetings", 1) to records)
                            val consumerRecords = ConsumerRecords(recordsPerPartition)
                            every { greetingKafkaConsumerMock.poll(any<Duration>()) } returns consumerRecords

                            val frames = emptyList<Frame>()

                            runBlocking {
                                webSocketClient.webSocket("/ws/greetings") {
                                    incoming.consumeEach {
                                        frames + it
                                    }
                                }
                            }

                            frames.isEmpty() shouldBe false
                            frames.size shouldBe 1
                        }
                    }
                }
            }
        }
    }
}) {
    private class TestContext {

    }
}