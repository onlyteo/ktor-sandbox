package com.onlyteo.sandbox.kafka.extensions

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecords
import java.time.Duration
import java.util.concurrent.atomic.AtomicBoolean

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
