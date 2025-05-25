package com.onlyteo.sandbox.lib.kafka.plugin

import com.onlyteo.sandbox.lib.kafka.listener.NoopConsumerRebalanceListener
import com.onlyteo.sandbox.lib.kafka.runner.KafkaConsumerAsyncRunner
import com.onlyteo.sandbox.lib.kafka.runner.KafkaConsumerCoroutineAsyncRunner
import com.onlyteo.sandbox.lib.kafka.runner.KafkaConsumerThreadPoolAsyncRunner
import io.ktor.events.EventDefinition
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationPlugin
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.application.ApplicationStopping
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.hooks.MonitoringEvent
import io.ktor.server.application.log
import io.ktor.utils.io.KtorDsl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

const val KAFKA_CONSUMER_PLUGIN_NAME = "KafkaConsumerPlugin"
val KafkaConsumerReady: EventDefinition<Application> = EventDefinition()
val KafkaConsumerStarting: EventDefinition<Application> = EventDefinition()
val KafkaConsumerStopping: EventDefinition<Application> = EventDefinition()

@KtorDsl
class KafkaConsumerPluginConfig {
    var asyncRunner: KafkaConsumerAsyncRunner? = null

    inline fun <reified K, V> useThreadPool(noinline block: ThreadPoolAsyncConfig<K, V>.() -> Unit) {
        val config = ThreadPoolAsyncConfig<K, V>().apply(block)
        this.asyncRunner = KafkaConsumerThreadPoolAsyncRunner(
            consumeFunction = checkNotNull(config.consumeFunction) { "Consume function must not be null" },
            errorFunction = checkNotNull(config.errorFunction) { "Error function must not be null" },
            topics = checkNotNull(config.topics) { "Kafka topic list must not be null" },
            kafkaConsumer = checkNotNull(config.kafkaConsumer) { "Kafka Consumer must not be null" },
            rebalanceListener = config.rebalanceListener ?: NoopConsumerRebalanceListener(),
            executorService = config.executorService ?: Executors.newSingleThreadExecutor()
        )
    }

    inline fun <reified K, V> useCoroutines(noinline block: CoroutinesAsyncConfig<K, V>.() -> Unit) {
        val config = CoroutinesAsyncConfig<K, V>().apply(block)
        this.asyncRunner = KafkaConsumerCoroutineAsyncRunner(
            consumeFunction = checkNotNull(config.consumeFunction) { "Consume function must not be null" },
            errorFunction = checkNotNull(config.errorFunction) { "Error function must not be null" },
            topics = checkNotNull(config.topics) { "Kafka topic list must not be null" },
            kafkaConsumer = checkNotNull(config.kafkaConsumer) { "Kafka Consumer must not be null" },
            rebalanceListener = config.rebalanceListener ?: NoopConsumerRebalanceListener(),
            coroutineScope = config.coroutineScope ?: CoroutineScope(Dispatchers.IO),
            coroutineDispatcher = config.coroutineDispatcher ?: Dispatchers.IO,
        )
    }

    class ThreadPoolAsyncConfig<K, V> {
        var consumeFunction: ((ConsumerRecords<K, V>) -> Unit)? = null
        var errorFunction: ((Throwable) -> Unit)? = null
        var topics: Collection<String>? = null
        var kafkaConsumer: KafkaConsumer<K, V>? = null
        var rebalanceListener: ConsumerRebalanceListener? = null
        var executorService: ExecutorService? = null
    }

    class CoroutinesAsyncConfig<K, V> {
        var consumeFunction: ((ConsumerRecords<K, V>) -> Unit)? = null
        var errorFunction: ((Throwable) -> Unit)? = null
        var topics: Collection<String>? = null
        var kafkaConsumer: KafkaConsumer<K, V>? = null
        var rebalanceListener: ConsumerRebalanceListener? = null
        var coroutineScope: CoroutineScope? = null
        var coroutineDispatcher: CoroutineDispatcher? = null
    }
}

val KafkaConsumerPlugin: ApplicationPlugin<KafkaConsumerPluginConfig> =
    createApplicationPlugin(KAFKA_CONSUMER_PLUGIN_NAME, ::KafkaConsumerPluginConfig) {
        application.log.info("Installing {}", KAFKA_CONSUMER_PLUGIN_NAME)
        val asyncRunner = checkNotNull(pluginConfig.asyncRunner) { "Async runner must not be null" }

        on(MonitoringEvent(ApplicationStarted)) { application ->
            asyncRunner.init()
            application.monitor.raise(KafkaConsumerReady, application)
        }

        on(MonitoringEvent(ApplicationStopping)) { application ->
            application.monitor.raise(KafkaConsumerStopping, application)
            asyncRunner.stop()
        }

        on(MonitoringEvent(KafkaConsumerReady)) { application ->
            application.monitor.raise(KafkaConsumerStarting, application)
            asyncRunner.start()
        }
    }
