package com.onlyteo.sandbox.app.context

import com.onlyteo.sandbox.lib.kafka.factory.buildJsonKafkaConsumer
import com.onlyteo.sandbox.lib.kafka.factory.buildJsonKafkaProducer
import com.onlyteo.sandbox.app.model.Greeting
import com.onlyteo.sandbox.app.model.Person
import com.onlyteo.sandbox.app.properties.ApplicationProperties
import com.onlyteo.sandbox.app.properties.ApplicationPropertiesHolder
import com.onlyteo.sandbox.properties.loadProperties
import com.onlyteo.sandbox.app.service.GreetingService
import kotlinx.coroutines.channels.Channel
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer

data class ApplicationContext(
    val properties: ApplicationProperties = loadProperties<ApplicationPropertiesHolder>().app,
    val personKafkaProducer: KafkaProducer<String, Person> = buildJsonKafkaProducer(properties.kafka.producer),
    val greetingKafkaConsumer: KafkaConsumer<String, Greeting> = buildJsonKafkaConsumer(properties.kafka.consumer),
    val greetingService: GreetingService = GreetingService(properties.kafka.producer, personKafkaProducer),
    val greetingChannel: Channel<Greeting> = Channel()
)
