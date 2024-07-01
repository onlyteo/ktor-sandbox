package com.onlyteo.sandbox.plugin.kafka

import io.ktor.server.application.ApplicationPlugin
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.application.ApplicationStopping
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.hooks.MonitoringEvent
import io.ktor.server.application.log
import io.ktor.util.KtorDsl
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler
import java.time.Duration

@KtorDsl
class KafkaStreamsPluginConfig {
    var streamsProperties: Map<String, Any>? = null
    var streamsTopology: Topology? = null
    var streamsExceptionHandler: StreamsUncaughtExceptionHandler? = null
}

val KafkaStreamsPlugin: ApplicationPlugin<KafkaStreamsPluginConfig> =
    createApplicationPlugin("KafkaStreams", ::KafkaStreamsPluginConfig) {
        val properties = checkNotNull(pluginConfig.streamsProperties) { "Kafka streams properties must not be null" }
        val topology = checkNotNull(pluginConfig.streamsTopology) { "Kafka streams topology must not be null" }
        val exceptionHandler =
            checkNotNull(pluginConfig.streamsExceptionHandler) { "Kafka streams exception handler must not be null" }

        val kafkaStreams = KafkaStreams(topology, StreamsConfig(properties))
        kafkaStreams.setUncaughtExceptionHandler(exceptionHandler)

        on(MonitoringEvent(ApplicationStarted)) { application ->
            application.log.info("Starting Kafka Streams")
            kafkaStreams.start()
        }

        on(MonitoringEvent(ApplicationStopping)) { application ->
            application.log.info("Stopping Kafka Streams")
            kafkaStreams.close(Duration.ofSeconds(5))
        }
    }