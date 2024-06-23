package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.application.ApplicationStopping
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import java.time.Duration

context(ApplicationContext)
fun Application.configureKafka(
    personKafkaProducer: KafkaProducer<String, Person>,
    greetingKafkaConsumer: KafkaConsumer<String, Greeting>
) {
    environment.monitor.subscribe(ApplicationStarted) {
    }

    environment.monitor.subscribe(ApplicationStopping) {
        personKafkaProducer.close(Duration.ofSeconds(2))
        greetingKafkaConsumer.close(Duration.ofSeconds(2))
    }
}