package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.properties.AppProperties
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer

fun buildPersonKafkaProducer(properties: AppProperties): KafkaProducer<String, Person> {
    return KafkaProducer(
        properties.kafka.producer.asMap(),
        StringSerializer(),
        buildJsonSerializer<Person>()
    )
}

fun buildGreetingKafkaConsumer(properties: AppProperties): KafkaConsumer<String, Greeting> {
    return KafkaConsumer(
        properties.kafka.consumer.asMap(),
        StringDeserializer(),
        buildJsonDeserializer<Greeting>()
    )
}

fun buildNoopConsumerRebalanceListener() = object : ConsumerRebalanceListener {
    override fun onPartitionsRevoked(partitions: MutableCollection<TopicPartition>?) {}
    override fun onPartitionsAssigned(partitions: MutableCollection<TopicPartition>?) {}
}
