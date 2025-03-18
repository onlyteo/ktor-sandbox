package com.onlyteo.sandbox.kafka.properties

import org.apache.kafka.clients.producer.ProducerConfig

data class KafkaProducerProperties(
    val clientId: String,
    val brokers: List<String>,
    val targetTopic: String
) {
    fun asMap(): Map<String, String> {
        return mapOf(
            ProducerConfig.CLIENT_ID_CONFIG to clientId,
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to brokers.joinToString(","),
            ProducerConfig.ACKS_CONFIG to "all",
            ProducerConfig.RETRIES_CONFIG to "0"
        )
    }
}
