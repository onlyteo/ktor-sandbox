package com.onlyteo.sandbox.context

import com.onlyteo.sandbox.config.buildGreetingKafkaConsumer
import com.onlyteo.sandbox.config.buildPersonKafkaProducer
import com.onlyteo.sandbox.config.loadProperties
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.properties.ApplicationProperties
import com.onlyteo.sandbox.properties.ApplicationPropertiesHolder
import kotlinx.coroutines.channels.Channel
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer

data class ApplicationContext(
    val properties: ApplicationProperties = loadProperties<ApplicationPropertiesHolder>().app,
    val personKafkaProducer: KafkaProducer<String, Person> = buildPersonKafkaProducer(properties),
    val greetingKafkaConsumer: KafkaConsumer<String, Greeting> = buildGreetingKafkaConsumer(properties),
    val greetingChannel: Channel<Greeting> = Channel()
)
