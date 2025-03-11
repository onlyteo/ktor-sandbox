package com.onlyteo.sandbox.properties

data class ApplicationPropertiesHolder(
    val app: ApplicationProperties
)

data class ApplicationProperties(
    val resources: ResourcesProperties,
    val security: SecurityProperties
)

data class ResourcesProperties(
    val prefixesFile: String
)

data class SecurityProperties(val oauth2: SecurityOAuth2Properties)

data class SecurityOAuth2Properties(
    val name: String,
    val provider: SecurityOAuth2ProviderProperties
)

data class SecurityOAuth2ProviderProperties(
    val issuer: String,
    val jwkUrl: String
)