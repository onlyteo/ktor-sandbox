package com.onlyteo.sandbox.plugin.custom

import com.onlyteo.sandbox.config.buildNoopConsumerRebalanceListener
import io.ktor.events.EventDefinition
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationPlugin
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.application.ApplicationStopping
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.hooks.MonitoringEvent
import io.ktor.server.application.log
import io.ktor.utils.io.KtorDsl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration
import java.util.concurrent.atomic.AtomicBoolean

val KafkaConsumerReady: EventDefinition<Application> = EventDefinition()
val KafkaConsumerStarting: EventDefinition<Application> = EventDefinition()
val KafkaConsumerStopping: EventDefinition<Application> = EventDefinition()

@KtorDsl
class KafkaConsumerPluginConfig<K, V> {
    var kafkaConsumer: KafkaConsumer<K, V>? = null
    var rebalanceListener: ConsumerRebalanceListener? = null
    var kafkaTopics: Collection<String>? = null
    var consumeFunction: (suspend (ConsumerRecords<K, V>) -> Unit)? = null
    var errorFunction: ((Throwable) -> Unit)? = null
    var pollTimeout: Duration? = null
    var closeTimeout: Duration? = null
}

fun <K, V> KafkaConsumerPlugin(): ApplicationPlugin<KafkaConsumerPluginConfig<K, V>> =
    createApplicationPlugin("KafkaConsumer", ::KafkaConsumerPluginConfig) {
        val kafkaConsumer = checkNotNull(pluginConfig.kafkaConsumer) { "Kafka Consumer must not be null" }
        val rebalanceListener = pluginConfig.rebalanceListener ?: buildNoopConsumerRebalanceListener()
        val kafkaTopics = checkNotNull(pluginConfig.kafkaTopics) { "Kafka Topics must not be null" }
        val consumeFunction = checkNotNull(pluginConfig.consumeFunction) { "Kafka consume function must not be null" }
        val errorFunction = checkNotNull(pluginConfig.errorFunction) { "Kafka error function must not be null" }
        val pollTimeout = pluginConfig.pollTimeout ?: Duration.ofMillis(500)
        val closeTimeout = pluginConfig.closeTimeout ?: Duration.ofSeconds(2)
        var consumerJob: Job? = null
        val consumerRunning = AtomicBoolean(false)

        on(MonitoringEvent(ApplicationStarted)) { application ->
            application.log.info("Kafka Consumer subscribing to topics {}", kafkaTopics)
            kafkaConsumer.subscribe(kafkaTopics, rebalanceListener)
            consumerRunning.set(true)
            application.monitor.raise(KafkaConsumerReady, application)
        }

        on(MonitoringEvent(ApplicationStopping)) { application ->
            application.log.info("Kafka Consumer stopping")
            application.monitor.raise(KafkaConsumerStopping, application)
            kafkaConsumer.unsubscribe()
            kafkaConsumer.close(closeTimeout)
            consumerRunning.set(false)
            consumerJob?.cancel()
        }

        on(MonitoringEvent(KafkaConsumerReady)) { application ->
            application.log.info("Kafka Consumer starting consume on topics {}", kafkaTopics)
            application.monitor.raise(KafkaConsumerStarting, application)
            consumerJob = application.launch(Dispatchers.IO) {
                try {
                    while (consumerRunning.get()) {
                        consumeFunction(kafkaConsumer.poll(pollTimeout))
                    }
                } catch (throwable: Throwable) {
                    errorFunction(throwable)
                }
            }
        }
    }