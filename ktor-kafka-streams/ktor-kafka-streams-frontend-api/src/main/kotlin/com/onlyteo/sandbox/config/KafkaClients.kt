package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.Person
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.serialization.StringSerializer

context(ApplicationContext)
fun buildKafkaProducer(): KafkaProducer<String, Person> {
    return KafkaProducer(
        properties.kafka.producer.asMap(),
        StringSerializer(),
        buildJsonSerializer<Person>()
    )
}