package com.onlyteo.sandbox.properties

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig

const val APPLICATION_PROPERTIES_FILE = "/application.yaml"

data class ApplicationPropertiesHolder(
    val app: ApplicationProperties
)

data class ApplicationProperties(
    val id: String,
    val kafka: KafkaProperties
)

data class KafkaProperties(
    val producer: KafkaProducerProperties,
    val consumer: KafkaConsumerProperties
)

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