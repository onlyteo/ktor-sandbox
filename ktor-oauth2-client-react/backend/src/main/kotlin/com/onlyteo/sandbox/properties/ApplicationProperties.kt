package com.onlyteo.sandbox.properties

const val APPLICATION_PROPERTIES_FILE = "/application.yaml"

data class ApplicationPropertiesHolder(
    val app: ApplicationProperties
)

data class ApplicationProperties(
    val resources: ResourcesProperties,
)

data class ResourcesProperties(
    val prefixesFile: String
)