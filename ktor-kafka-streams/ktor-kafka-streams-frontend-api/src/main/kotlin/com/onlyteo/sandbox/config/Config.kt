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
    val integrations: IntegrationsConfig
)

data class IntegrationsConfig(val greetingService: IntegrationConfig)

data class IntegrationConfig(val url: String)
