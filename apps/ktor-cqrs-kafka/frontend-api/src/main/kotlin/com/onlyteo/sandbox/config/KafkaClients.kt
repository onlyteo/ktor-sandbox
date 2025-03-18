package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.properties.KafkaConsumerProperties
import com.onlyteo.sandbox.properties.KafkaProducerProperties
import com.onlyteo.sandbox.serialization.factory.buildJsonDeserializer
import com.onlyteo.sandbox.serialization.factory.buildJsonSerializer
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.time.Duration
import java.util.concurrent.atomic.AtomicBoolean

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

fun <K, V> Consumer<K, V>.consumeRecords(
    consumeFunction: ((ConsumerRecords<K, V>) -> Unit),
    pollTimeout: Duration = Duration.ofMillis(500)
) {
    consumeFunction(poll(pollTimeout))
}

fun <K, V> Consumer<K, V>.consumeSequence(
    close: AtomicBoolean,
    pollTimeout: Duration = Duration.ofMillis(500),
    closeTimeout: Duration = Duration.ofMillis(1000)
): Sequence<ConsumerRecords<K, V>> = generateSequence {
    if (close.get()) {
        close(closeTimeout)
        null
    } else {
        poll(pollTimeout)
    }
}

fun <K, V> Consumer<K, V>.abort() {
    unsubscribe()
    close(Duration.ofSeconds(2))
}
