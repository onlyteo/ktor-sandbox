package com.onlyteo.sandbox.lib.kafka.factory

import com.onlyteo.sandbox.lib.kafka.properties.KafkaConsumerProperties
import com.onlyteo.sandbox.lib.kafka.properties.KafkaProducerProperties
import com.onlyteo.sandbox.lib.serialization.factory.buildJsonDeserializer
import com.onlyteo.sandbox.lib.serialization.factory.buildJsonSerializer
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