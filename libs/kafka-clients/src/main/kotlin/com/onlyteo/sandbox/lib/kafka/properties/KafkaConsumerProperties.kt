package com.onlyteo.sandbox.lib.kafka.properties

import org.apache.kafka.clients.consumer.ConsumerConfig

data class KafkaConsumerProperties(
    val clientId: String,
    val groupId: String,
    val brokers: List<String>,
    val sourceTopic: String
) {
    fun asMap(): Map<String, String> {
        return mapOf(
            ConsumerConfig.CLIENT_ID_CONFIG to clientId,
            ConsumerConfig.GROUP_ID_CONFIG to groupId,
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to brokers.joinToString(","),
        )
    }
}
