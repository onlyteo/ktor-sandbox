package com.onlyteo.sandbox.app.properties

import com.onlyteo.sandbox.lib.kafka.properties.KafkaConsumerProperties
import com.onlyteo.sandbox.lib.kafka.properties.KafkaProducerProperties

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
