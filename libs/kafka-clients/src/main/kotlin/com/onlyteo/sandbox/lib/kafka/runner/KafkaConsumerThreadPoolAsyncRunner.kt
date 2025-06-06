package com.onlyteo.sandbox.lib.kafka.runner

import com.onlyteo.sandbox.lib.async.runner.ThreadPoolAsyncRunner
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.slf4j.LoggerFactory
import java.time.Duration
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class KafkaConsumerThreadPoolAsyncRunner<K, V>(
    private val consumeFunction: (ConsumerRecords<K, V>) -> Unit,
    private val errorFunction: (Throwable) -> Unit,
    private val topics: Collection<String>,
    private val kafkaConsumer: KafkaConsumer<K, V>,
    private val rebalanceListener: ConsumerRebalanceListener,
    executorService: ExecutorService = Executors.newSingleThreadExecutor()
) : ThreadPoolAsyncRunner<ConsumerRecords<K, V>>(executorService), KafkaConsumerAsyncRunner {
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