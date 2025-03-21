package com.onlyteo.sandbox.properties

import com.onlyteo.sandbox.environment.currentEnvironment
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.ExperimentalHoplite
import com.sksamuel.hoplite.addResourceSource
import com.sksamuel.hoplite.env.Environment

@OptIn(ExperimentalHoplite::class)
inline fun <reified T : Any> loadProperties(environment: Environment = currentEnvironment()): T {
    return ConfigLoaderBuilder.default()
        .withExplicitSealedTypes()
        .addResourceSource("/application.yaml")
        .addResourceSource("/application-${environment.name}.yaml")
        .build()
        .loadConfigOrThrow<T>()
}