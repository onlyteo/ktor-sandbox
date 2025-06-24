package com.onlyteo.sandbox.app.properties

import com.onlyteo.sandbox.lib.kafka.properties.KafkaStreamsProperties

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
