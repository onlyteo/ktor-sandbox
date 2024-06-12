package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer

context(ApplicationContext)
fun buildPersonKafkaProducer(): KafkaProducer<String, Person> {
    return KafkaProducer(
        properties.kafka.producer.asMap(),
        StringSerializer(),
        buildJsonSerializer<Person>()
    )
}

context(ApplicationContext)
fun buildGreetingKafkaConsumer(): KafkaConsumer<String, Greeting> {
    return KafkaConsumer(
        properties.kafka.consumer.asMap(),
        StringDeserializer(),
        buildJsonDeserializer<Greeting>()
    )
}