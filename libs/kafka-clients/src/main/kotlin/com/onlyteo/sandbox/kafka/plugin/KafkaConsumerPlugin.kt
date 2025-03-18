package com.onlyteo.sandbox.kafka.plugin

import com.onlyteo.sandbox.async.runner.AsyncRunner
import com.onlyteo.sandbox.async.runner.CoroutineAsyncRunner
import com.onlyteo.sandbox.async.runner.ThreadPoolAsyncRunner
import com.onlyteo.sandbox.kafka.extensions.abort
import com.onlyteo.sandbox.kafka.extensions.consumeRecords
import com.onlyteo.sandbox.kafka.listener.NoopConsumerRebalanceListener
import io.ktor.events.EventDefinition
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationPlugin
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.application.ApplicationStopping
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.hooks.MonitoringEvent
import io.ktor.server.application.log
import io.ktor.utils.io.KtorDsl
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer

val KafkaConsumerReady: EventDefinition<Application> = EventDefinition()
val KafkaConsumerStarting: EventDefinition<Application> = EventDefinition()
val KafkaConsumerStopping: EventDefinition<Application> = EventDefinition()

@KtorDsl
class KafkaConsumerPluginConfig {
    var kafkaTopics: Collection<String>? = null
    var kafkaConsumer: KafkaConsumer<*, *>? = null
    var rebalanceListener: ConsumerRebalanceListener? = NoopConsumerRebalanceListener()
    var asyncRunner: AsyncRunner<Application>? = null

    inline fun <reified K, V> useThreadPool(noinline block: PluginConfig<K, V>.() -> Unit) {
        val config = PluginConfig<K, V>().apply(block)
        check(this.kafkaConsumer == null) { "Don't set 'kafkaConsumer' value outside the 'useCoroutines' block" }
        check(this.asyncRunner == null) { "Don't set 'asyncRunner' value outside the 'useCoroutines' block" }
        val kafkaConsumer = checkNotNull(config.kafkaConsumer) { "Kafka Consumer is null" }
        val consumeFunction = checkNotNull(config.consumeFunction) { "Kafka consume function must not be null" }
        val errorFunction = checkNotNull(config.errorFunction) { "Kafka error function must not be null" }
        this.kafkaConsumer = kafkaConsumer
        this.asyncRunner = ThreadPoolAsyncRunner(
            taskFunction = {
                kafkaConsumer.consumeRecords(consumeFunction)
            },
            errorFunction = errorFunction,
            abortFunction = kafkaConsumer::abort
        )
    }

    inline fun <reified K, V> useCoroutines(noinline block: PluginConfig<K, V>.() -> Unit) {
        val config = PluginConfig<K, V>().apply(block)
        check(this.kafkaConsumer == null) { "Don't set 'kafkaConsumer' value outside the 'useCoroutines' block" }
        check(this.asyncRunner == null) { "Don't set 'asyncRunner' value outside the 'useCoroutines' block" }
        val kafkaConsumer = checkNotNull(config.kafkaConsumer) { "Kafka Consumer is null" }
        val consumeFunction = checkNotNull(config.consumeFunction) { "Kafka consume function must not be null" }
        val errorFunction = checkNotNull(config.errorFunction) { "Kafka error function must not be null" }
        this.kafkaConsumer = kafkaConsumer
        this.asyncRunner = CoroutineAsyncRunner(
            taskFunction = {
                kafkaConsumer.consumeRecords(consumeFunction)
            },
            errorFunction = errorFunction,
            abortFunction = kafkaConsumer::abort
        )
    }

    class PluginConfig<K, V> {
        var kafkaConsumer: KafkaConsumer<K, V>? = null
        var consumeFunction: ((ConsumerRecords<K, V>) -> Unit)? = null
        var errorFunction: ((Throwable) -> Unit)? = null
    }
}

val KafkaConsumerPlugin: ApplicationPlugin<KafkaConsumerPluginConfig> =
    createApplicationPlugin("KafkaConsumer", ::KafkaConsumerPluginConfig) {
        val kafkaTopics = checkNotNull(pluginConfig.kafkaTopics) {
            "Kafka Topics must not be null"
        }
        val kafkaConsumer = checkNotNull(pluginConfig.kafkaConsumer) {
            "Kafka Consumer must not be null"
        }
        val rebalanceListener = checkNotNull(pluginConfig.rebalanceListener) {
            "Kafka Consumer rebalance listener must not be null"
        }
        val asyncRunner = checkNotNull(pluginConfig.asyncRunner) {
            "Kafka Consumer rebalance listener must not be null"
        }

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
