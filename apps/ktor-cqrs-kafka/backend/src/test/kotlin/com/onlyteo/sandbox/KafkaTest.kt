package com.onlyteo.sandbox

import com.onlyteo.sandbox.app.model.Greeting
import com.onlyteo.sandbox.app.model.Person
import com.onlyteo.sandbox.lib.serialization.factory.buildJsonDeserializer
import com.onlyteo.sandbox.lib.serialization.factory.buildJsonSerializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.time.Duration
import java.util.*

@Disabled("Should only be run manually")
class KafkaTest {

    @Test
    fun `should publish person messages to Kafka`() {
        val properties = Properties()
        properties[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        properties[ProducerConfig.CLIENT_ID_CONFIG] = "test-person-producer"
        properties[ProducerConfig.ACKS_CONFIG] = "all"
        properties[ProducerConfig.RETRIES_CONFIG] = 0

        val personKafkaProducer =
            KafkaProducer(properties, StringSerializer(), buildJsonSerializer<Person>())

        val person = Person("John")

        try {
            personKafkaProducer.send(ProducerRecord("persons", person.name, person))
        } finally {
            personKafkaProducer.close(Duration.ofSeconds(5))
        }
    }

    @Test
    fun `should consume greeting messages from Kafka`() {
        val properties = Properties()
        properties[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        properties[ConsumerConfig.CLIENT_ID_CONFIG] = "test-greeting-consumer"
        properties[ConsumerConfig.GROUP_ID_CONFIG] = "test-greeting-consumer-group"

        val greetingKafkaConsumer =
            KafkaConsumer(properties, StringDeserializer(), buildJsonDeserializer<Greeting>())

        try {
            greetingKafkaConsumer.subscribe(listOf("greetings"))
            val records = greetingKafkaConsumer.poll(Duration.ofMillis(100))
            for (record in records) {
                println(record.value())
            }
        } finally {
            greetingKafkaConsumer.close(Duration.ofSeconds(5))
        }
    }
}