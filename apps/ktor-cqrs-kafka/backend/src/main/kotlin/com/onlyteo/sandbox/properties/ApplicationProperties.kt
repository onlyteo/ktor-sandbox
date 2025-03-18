package com.onlyteo.sandbox.properties

import com.onlyteo.sandbox.kafka.properties.KafkaStreamsProperties

data class ApplicationPropertiesHolder(
    val app: ApplicationProperties
)

data class ApplicationProperties(
    val id: String,
    val resources: ResourcesProperties,
    val kafka: KafkaProperties
)

data class ResourcesProperties(
    val prefixesFile: String
)

data class KafkaProperties(
    val streams: KafkaStreamsProperties
)
