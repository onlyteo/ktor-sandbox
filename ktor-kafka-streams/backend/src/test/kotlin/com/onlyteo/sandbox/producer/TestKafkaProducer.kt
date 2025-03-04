package com.onlyteo.sandbox.producer

import com.onlyteo.sandbox.config.buildJsonKafkaProducer
import com.onlyteo.sandbox.config.loadProperties
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.properties.KafkaProducerProperties
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import java.time.Duration
import java.util.stream.IntStream

fun main() {
    val logger = LoggerFactory.getLogger("com.onlyteo.sandbox.producer")

    val personKafkaProducerProperties = loadProperties<KafkaProducerProperties>("person-kafka-client.yaml")
    val greetingKafkaProducerProperties = loadProperties<KafkaProducerProperties>("greeting-kafka-client.yaml")

    val personKafkaProducer = buildJsonKafkaProducer<Person>(personKafkaProducerProperties)
    val greetingKafkaProducer = buildJsonKafkaProducer<Greeting>(greetingKafkaProducerProperties)

    with(personKafkaProducerProperties) {
        IntStream.rangeClosed(1, 100).forEach { i ->
            val key = i.toString()
            val value = Person("Name $i")
            logger.info("Sending $key to $value")
            personKafkaProducer.send(ProducerRecord(targetTopic, key, value)).get()
        }
    }

    personKafkaProducer.close(Duration.ofSeconds(5))

    with(greetingKafkaProducerProperties) {
        IntStream.rangeClosed(1, 10).forEach { i ->
            IntStream.rangeClosed(1, 100).forEach { j ->
                val key = j.toString()
                val value = Greeting("Hello $i to Name $j!")
                logger.info("Sending $key to $value")
                greetingKafkaProducer.send(ProducerRecord(targetTopic, key, value)).get()
            }
        }
    }

    greetingKafkaProducer.close(Duration.ofSeconds(5))
}