package com.onlyteo.sandbox.lib.kafka.properties

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsConfig
import java.time.Duration

data class KafkaStreamsProperties(
    val id: String,
    val brokers: List<String>,
    val sourceTopic: String,
    val targetTopic: String,
    val stateStore: String,
    val processor: String,
    val punctuatorSchedule: Duration,
    val processingDelay: Duration
) {
    fun asMap(): Map<String, Any> {
        return mapOf(
            StreamsConfig.APPLICATION_ID_CONFIG to id,
            StreamsConfig.BOOTSTRAP_SERVERS_CONFIG to brokers.joinToString(","),
            StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG to Serdes.String().javaClass,
            StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG to Serdes.String().javaClass,
        )
    }
}
