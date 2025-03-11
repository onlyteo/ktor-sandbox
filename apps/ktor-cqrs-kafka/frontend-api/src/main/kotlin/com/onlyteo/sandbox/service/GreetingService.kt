package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.config.buildLogger
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.properties.KafkaProducerProperties
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata

class GreetingService(
    private val kafkaProducerProperties: KafkaProducerProperties,
    private val personKafkaProducer: KafkaProducer<String, Person>
) {
    private val logger = buildLogger

    fun sendMessage(person: Person): RecordMetadata? {
        val topic = kafkaProducerProperties.targetTopic
        logger.info("Sending person \"{}\" on topic \"{}\"", person.name, topic)
        return personKafkaProducer.send(ProducerRecord(topic, person.name, person)).get()
    }
}