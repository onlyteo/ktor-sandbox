package com.onlyteo.sandbox.lib.kafka.plugin

import io.ktor.server.application.ApplicationPlugin
import io.ktor.server.application.ApplicationStopping
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.hooks.MonitoringEvent
import io.ktor.server.application.log
import io.ktor.utils.io.KtorDsl
import org.apache.kafka.clients.producer.KafkaProducer
import java.time.Duration

const val KAFKA_PRODUCER_PLUGIN_NAME = "KafkaProducerPlugin"

@KtorDsl
class KafkaProducerPluginConfig {
    var kafkaProducer: KafkaProducer<*, *>? = null
    var closeTimeout: Duration? = Duration.ofSeconds(5)
}

val KafkaProducerPlugin: ApplicationPlugin<KafkaProducerPluginConfig> =
    createApplicationPlugin(KAFKA_PRODUCER_PLUGIN_NAME, ::KafkaProducerPluginConfig) {
        application.log.info("Installing {}", KAFKA_PRODUCER_PLUGIN_NAME)
        val kafkaProducer = checkNotNull(pluginConfig.kafkaProducer) { "Kafka Consumer must not be null" }
        val closeTimeout = checkNotNull(pluginConfig.closeTimeout) { "Close timeout must not be null" }

        on(MonitoringEvent(ApplicationStopping)) { application ->
            application.log.info("Stopping Kafka Producer")
            kafkaProducer.close(closeTimeout)
        }
    }