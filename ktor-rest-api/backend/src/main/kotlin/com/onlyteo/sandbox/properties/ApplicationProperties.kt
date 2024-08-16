package com.onlyteo.sandbox.properties

data class ApplicationPropertiesHolder(
    val app: ApplicationProperties
)

data class ApplicationProperties(
    val resources: ResourcesProperties,
)

data class ResourcesProperties(
    val prefixesFile: String
)