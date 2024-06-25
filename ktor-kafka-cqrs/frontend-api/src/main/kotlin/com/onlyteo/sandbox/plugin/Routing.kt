package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.routes.greetingRouting
import com.onlyteo.sandbox.routes.staticRouting
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.IgnoreTrailingSlash
import io.ktor.server.routing.routing
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer

context(ApplicationContext)
fun Application.configureRouting(
    personKafkaProducer: KafkaProducer<String, Person>,
    greetingKafkaConsumer: KafkaConsumer<String, Greeting>
) {
    install(IgnoreTrailingSlash)
    routing {
        staticRouting()
        greetingRouting(personKafkaProducer, greetingKafkaConsumer)
    }
}