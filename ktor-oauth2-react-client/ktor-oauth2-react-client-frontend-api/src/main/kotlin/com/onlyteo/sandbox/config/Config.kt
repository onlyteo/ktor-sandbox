package com.onlyteo.sandbox.config

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource

val configSupplier: Config
    get() = ConfigLoaderBuilder.default()
        .addResourceSource("/application.yaml")
        .build()
        .loadConfigOrThrow<Config>()

data class Config(val app: AppConfig)

data class AppConfig(
    val integrations: IntegrationsConfig,
    val security: SecurityConfig
)

data class IntegrationsConfig(val greetingService: IntegrationConfig)

data class IntegrationConfig(val url: String)

data class SecurityConfig(val oauth2: SecurityOAuth2Config)

data class SecurityOAuth2Config(
    val name: String,
    val callbackUrl: String,
    val provider: SecurityOAuth2ProviderConfig
)

data class SecurityOAuth2ProviderConfig(
    val name: String,
    val authorizeUrl: String,
    val tokenUrl: String,
    val clientId: String,
    val clientSecret: String,
    val scopes: List<String> = emptyList()
)