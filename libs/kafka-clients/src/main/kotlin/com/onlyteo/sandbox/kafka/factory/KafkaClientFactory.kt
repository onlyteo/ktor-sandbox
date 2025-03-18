package com.onlyteo.sandbox.kafka.factory

import com.onlyteo.sandbox.kafka.properties.KafkaConsumerProperties
import com.onlyteo.sandbox.kafka.properties.KafkaProducerProperties
import com.onlyteo.sandbox.serialization.factory.buildJsonDeserializer
import com.onlyteo.sandbox.serialization.factory.buildJsonSerializer
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer

inline fun <reified V> buildJsonKafkaProducer(properties: KafkaProducerProperties): KafkaProducer<String, V> {
    return KafkaProducer(
        properties.asMap(),
        StringSerializer(),
        buildJsonSerializer<V>()
    )
}

inline fun <reified V> buildJsonKafkaConsumer(properties: KafkaConsumerProperties): KafkaConsumer<String, V> {
    return KafkaConsumer(
        properties.asMap(),
        StringDeserializer(),
        buildJsonDeserializer<V>()
    )
}