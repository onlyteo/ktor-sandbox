package com.onlyteo.sandbox.lib.kafka.runner

import com.onlyteo.sandbox.lib.async.runner.CoroutineAsyncRunner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.slf4j.LoggerFactory
import java.time.Duration

class KafkaConsumerCoroutineAsyncRunner<K, V>(
    private val consumeFunction: (ConsumerRecords<K, V>) -> Unit,
    private val errorFunction: (Throwable) -> Unit,
    private val topics: Collection<String>,
    private val kafkaConsumer: KafkaConsumer<K, V>,
    private val rebalanceListener: ConsumerRebalanceListener,
    coroutineScope: CoroutineScope,
    coroutineDispatcher: CoroutineDispatcher
) : CoroutineAsyncRunner<ConsumerRecords<K, V>>(coroutineScope, coroutineDispatcher), KafkaConsumerAsyncRunner {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun init() {
        logger.info("Kafka Consumer subscribing to topics {}", topics)
        kafkaConsumer.subscribe(topics, rebalanceListener)
    }

    override fun start() {
        run(
            task = { kafkaConsumer.poll(Duration.ofMillis(100)) },
            onSuccess = consumeFunction,
            onFailure = errorFunction
        )
    }

    override fun stop() {
        abort(onAbort = {
            kafkaConsumer.unsubscribe()
            kafkaConsumer.close(Duration.ofMillis(500))
        })
    }
}