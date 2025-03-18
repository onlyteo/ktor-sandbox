package com.onlyteo.sandbox.context

import com.onlyteo.sandbox.config.loadProperties
import com.onlyteo.sandbox.kafka.factory.buildJsonKafkaConsumer
import com.onlyteo.sandbox.kafka.factory.buildJsonKafkaProducer
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.properties.ApplicationProperties
import com.onlyteo.sandbox.properties.ApplicationPropertiesHolder
import com.onlyteo.sandbox.service.GreetingService
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
