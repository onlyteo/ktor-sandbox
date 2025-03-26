package com.onlyteo.sandbox.kafka.plugin

import com.onlyteo.sandbox.kafka.properties.KafkaStreamsProperties
import io.ktor.events.EventDefinition
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationPlugin
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.application.ApplicationStopping
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.hooks.MonitoringEvent
import io.ktor.server.application.log
import io.ktor.utils.io.KtorDsl
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler
import java.time.Duration

const val KAFKA_STREAMS_PLUGIN_NAME = "KafkaStreamsPlugin"
val KafkaStreamsStarting: EventDefinition<Application> = EventDefinition()
val KafkaStreamsStopping: EventDefinition<Application> = EventDefinition()

@KtorDsl
class KafkaStreamsPluginConfig {
    var properties: KafkaStreamsProperties? = null
    var kafkaTopology: Topology? = null
    var exceptionHandler: StreamsUncaughtExceptionHandler? = null
}

val KafkaStreamsPlugin: ApplicationPlugin<KafkaStreamsPluginConfig> =
    createApplicationPlugin(KAFKA_STREAMS_PLUGIN_NAME, ::KafkaStreamsPluginConfig) {
        application.log.info("Installing {}", KAFKA_STREAMS_PLUGIN_NAME)
        val properties = checkNotNull(pluginConfig.properties) { "Kafka Streams properties must not be null" }
        val kafkaTopology = checkNotNull(pluginConfig.kafkaTopology) { "Kafka Streams topology must not be null" }
        val exceptionHandler = checkNotNull(pluginConfig.exceptionHandler) {
            "Kafka Streams exception handler must not be null"
        }

        val kafkaStreams = KafkaStreams(kafkaTopology, StreamsConfig(properties.asMap()))
        kafkaStreams.setUncaughtExceptionHandler(exceptionHandler)

        on(MonitoringEvent(ApplicationStarted)) { application ->
            application.log.info("Starting Kafka Streams")
            application.monitor.raise(KafkaStreamsStarting, application)
            kafkaStreams.start()
        }

        on(MonitoringEvent(ApplicationStopping)) { application ->
            application.log.info("Stopping Kafka Streams")
            application.monitor.raise(KafkaStreamsStopping, application)
            kafkaStreams.close(Duration.ofSeconds(5))
        }
    }