package com.onlyteo.sandbox.properties

const val APPLICATION_PROPERTIES_FILE = "/application.yaml"

data class ApplicationPropertiesHolder(val app: ApplicationProperties)

data class ApplicationProperties(
    val integrations: IntegrationsProperties,
    val security: SecurityProperties
)

data class IntegrationsProperties(val greetingService: IntegrationProperties)

data class IntegrationProperties(val url: String)

data class SecurityProperties(val oauth2: SecurityOAuth2Properties)

data class SecurityOAuth2Properties(
    val name: String,
    val callbackUrl: String,
    val provider: SecurityOAuth2ProviderProperties
)

data class SecurityOAuth2ProviderProperties(
    val name: String,
    val authorizeUrl: String,
    val tokenUrl: String,
    val clientId: String,
    val clientSecret: String,
    val scopes: List<String> = emptyList()
)