package com.onlyteo.sandbox.properties

data class ApplicationPropertiesHolder(val app: ApplicationProperties)

data class ApplicationProperties(
    val integrations: IntegrationsProperties,
)

data class IntegrationsProperties(val backend: IntegrationProperties)

data class IntegrationProperties(val url: String)