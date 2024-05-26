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
import java.time.Duration

@KtorDsl
class KafkaStreamsPluginConfig {
    var properties: Map<String, Any>? = null
    var topology: Topology? = null
}

val KafkaStreamsPlugin: ApplicationPlugin<KafkaStreamsPluginConfig> =
    createApplicationPlugin("KafkaStreams", ::KafkaStreamsPluginConfig) {
        val properties = requireNotNull(pluginConfig.properties)
        val topology = requireNotNull(pluginConfig.topology)

        val kafkaStreams = KafkaStreams(topology, StreamsConfig(properties))

        on(MonitoringEvent(ApplicationStarted)) { application ->
            application.log.info("Starting Kafka Streams")
            kafkaStreams.start()
        }

        on(MonitoringEvent(ApplicationStopping)) { application ->
            application.log.info("Stopping Kafka Streams")
            kafkaStreams.close(Duration.ofSeconds(5))
        }
    }