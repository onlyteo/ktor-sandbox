package com.onlyteo.sandbox.app.service

import com.onlyteo.sandbox.lib.kafka.properties.KafkaProducerProperties
import com.onlyteo.sandbox.lib.logging.factory.buildLogger
import com.onlyteo.sandbox.app.model.Person
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