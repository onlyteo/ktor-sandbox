package com.onlyteo.sandbox.properties

import com.onlyteo.sandbox.kafka.properties.KafkaConsumerProperties
import com.onlyteo.sandbox.kafka.properties.KafkaProducerProperties

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
