package com.onlyteo.sandbox.properties

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsConfig

data class ApplicationPropertiesHolder(
    val app: ApplicationProperties
)

data class ApplicationProperties(
    val id: String,
    val kafka: KafkaProperties
)

data class KafkaProperties(
    val brokers: List<String>,
    val streams: KafkaStreamsProperties
) {
    fun asMap(): Map<String, Any> {
        return mapOf(
            StreamsConfig.BOOTSTRAP_SERVERS_CONFIG to brokers.joinToString(",")
        ) + streams.asMap()
    }
}

data class KafkaStreamsProperties(
    val id: String,
    val sourceTopic: String,
    val targetTopic: String,
    val stateStore: String,
    val processor: String
) {
    fun asMap(): Map<String, Any> {
        return mapOf(
            StreamsConfig.APPLICATION_ID_CONFIG to id,
            StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG to Serdes.String().javaClass,
            StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG to Serdes.String().javaClass,
        )
    }
}