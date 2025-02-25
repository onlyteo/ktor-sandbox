package com.onlyteo.sandbox.plugin.custom

import com.onlyteo.sandbox.listener.NoopConsumerRebalanceListener
import com.onlyteo.sandbox.runner.AsyncRunner
import com.onlyteo.sandbox.runner.CoroutineAsyncRunner
import com.onlyteo.sandbox.runner.ThreadPoolAsyncRunner
import io.ktor.events.EventDefinition
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationPlugin
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.application.ApplicationStopping
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.hooks.MonitoringEvent
import io.ktor.server.application.log
import io.ktor.utils.io.KtorDsl
import kotlinx.coroutines.CoroutineScope
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration

val KafkaConsumerReady: EventDefinition<Application> = EventDefinition()
val KafkaConsumerStarting: EventDefinition<Application> = EventDefinition()
val KafkaConsumerStopping: EventDefinition<Application> = EventDefinition()

@KtorDsl
class KafkaConsumerPluginConfig<K, V> {
    var kafkaTopics: Collection<String>? = null
    var consumeFunction: ((ConsumerRecords<K, V>) -> Unit)? = null
    var errorFunction: ((Throwable) -> Unit)? = null
    var kafkaConsumer: KafkaConsumer<K, V>? = null
    var rebalanceListener: ConsumerRebalanceListener? = NoopConsumerRebalanceListener()
}

@Suppress("FunctionName")
fun <K, V> KafkaConsumerPlugin(): ApplicationPlugin<KafkaConsumerPluginConfig<K, V>> =
    createApplicationPlugin("KafkaConsumer", ::KafkaConsumerPluginConfig) {
        val kafkaConsumer = checkNotNull(pluginConfig.kafkaConsumer) { "Kafka Consumer must not be null" }
        val rebalanceListener =
            checkNotNull(pluginConfig.rebalanceListener) { "Kafka Consumer rebalance listener must not be null" }
        val kafkaTopics = checkNotNull(pluginConfig.kafkaTopics) { "Kafka Topics must not be null" }
        val consumeFunction = checkNotNull(pluginConfig.consumeFunction) { "Kafka consume function must not be null" }
        val errorFunction = checkNotNull(pluginConfig.errorFunction) { "Kafka error function must not be null" }
        val asyncRunner = buildCoroutineAsyncRunner(
            consumeFunction = consumeFunction,
            errorFunction = errorFunction,
            kafkaConsumer = kafkaConsumer
        )

        on(MonitoringEvent(ApplicationStarted)) { application ->
            application.log.info("Kafka Consumer subscribing to topics {}", kafkaTopics)
            kafkaConsumer.subscribe(kafkaTopics, rebalanceListener)
            application.monitor.raise(KafkaConsumerReady, application)
        }

        on(MonitoringEvent(ApplicationStopping)) { application ->
            application.log.info("Kafka Consumer stopping")
            application.monitor.raise(KafkaConsumerStopping, application)
            asyncRunner.abort()
        }

        on(MonitoringEvent(KafkaConsumerReady)) { application ->
            application.log.info("Kafka Consumer starting consume on topics {}", kafkaTopics)
            application.monitor.raise(KafkaConsumerStarting, application)
            asyncRunner.run(application)
        }
    }

private fun <K, V> KafkaConsumer<K, V>.consume(handle: ((ConsumerRecords<K, V>) -> Unit)) {
    handle(poll(Duration.ofMillis(500)))
}

private fun KafkaConsumer<*, *>.abort() {
    unsubscribe()
    close(Duration.ofSeconds(2))
}

private fun <K, V> buildThreadPoolAsyncRunner(
    consumeFunction: ((ConsumerRecords<K, V>) -> Unit),
    errorFunction: ((Throwable) -> Unit),
    kafkaConsumer: KafkaConsumer<K, V>
): AsyncRunner<Nothing> = ThreadPoolAsyncRunner(
    runFunction = {
        kafkaConsumer.consume(consumeFunction)
    },
    errorFunction = errorFunction,
    abortFunction = kafkaConsumer::abort
)

private fun <K, V> buildCoroutineAsyncRunner(
    consumeFunction: ((ConsumerRecords<K, V>) -> Unit),
    errorFunction: ((Throwable) -> Unit),
    kafkaConsumer: KafkaConsumer<K, V>
): AsyncRunner<CoroutineScope> = CoroutineAsyncRunner(
    runFunction = {
        kafkaConsumer.consume(consumeFunction)
    },
    errorFunction = errorFunction,
    abortFunction = kafkaConsumer::abort
)
