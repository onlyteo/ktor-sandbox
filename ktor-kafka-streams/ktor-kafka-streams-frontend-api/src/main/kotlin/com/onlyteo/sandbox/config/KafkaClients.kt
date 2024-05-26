package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.properties.KafkaProducerProperties
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.serialization.StringSerializer

fun buildKafkaProducer(properties: KafkaProducerProperties): KafkaProducer<String, Person> {
    return KafkaProducer(
        properties.asMap(),
        StringSerializer(),
        buildJsonSerializer<Person>()
    )
}