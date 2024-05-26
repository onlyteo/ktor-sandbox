package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.properties.KafkaProducerProperties
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord

class GreetingService(
    private val properties: KafkaProducerProperties,
    private val kafkaProducer: KafkaProducer<String, Person>
) {

    fun publishPerson(person: Person) {
        kafkaProducer.send(ProducerRecord(properties.targetTopic, person.name, person))
    }
}