package com.onlyteo.sandbox.plugin.custom

import com.onlyteo.sandbox.handler.withUncaughtExceptionHandler
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

val KafkaStreamsStarting: EventDefinition<Application> = EventDefinition()
val KafkaStreamsStopping: EventDefinition<Application> = EventDefinition()

@KtorDsl
class KafkaStreamsPluginConfig {
    var streamsProperties: Map<String, Any>? = null
    var streamsTopology: Topology? = null
    var streamsExceptionHandler: StreamsUncaughtExceptionHandler? = null
}

val KafkaStreamsPlugin: ApplicationPlugin<KafkaStreamsPluginConfig> =
    createApplicationPlugin("KafkaStreams", ::KafkaStreamsPluginConfig) {
        val properties = checkNotNull(pluginConfig.streamsProperties) {
            "Kafka Streams properties must not be null"
        }
        val topology = checkNotNull(pluginConfig.streamsTopology) {
            "Kafka Streams topology must not be null"
        }
        val exceptionHandler = checkNotNull(pluginConfig.streamsExceptionHandler) {
            "Kafka Streams exception handler must not be null"
        }

        val kafkaStreams = KafkaStreams(topology, StreamsConfig(properties))
            .withUncaughtExceptionHandler(exceptionHandler)

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