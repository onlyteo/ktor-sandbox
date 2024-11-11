package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.properties.ApplicationProperties
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer

fun buildPersonKafkaProducer(properties: ApplicationProperties): KafkaProducer<String, Person> {
    return KafkaProducer(
        properties.kafka.producer.asMap(),
        StringSerializer(),
        buildJsonSerializer<Person>()
    )
}

fun buildGreetingKafkaConsumer(properties: ApplicationProperties): KafkaConsumer<String, Greeting> {
    return KafkaConsumer(
        properties.kafka.consumer.asMap(),
        StringDeserializer(),
        buildJsonDeserializer<Greeting>()
    )
}