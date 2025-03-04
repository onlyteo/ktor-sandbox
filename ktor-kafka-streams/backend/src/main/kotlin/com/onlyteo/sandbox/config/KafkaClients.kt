package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.properties.KafkaProducerProperties
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.serialization.StringSerializer

inline fun <reified V> buildJsonKafkaProducer(properties: KafkaProducerProperties): KafkaProducer<String, V> {
    return KafkaProducer(
        properties.asMap(),
        StringSerializer(),
        buildJsonSerializer<V>()
    )
}
