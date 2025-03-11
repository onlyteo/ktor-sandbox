package com.onlyteo.sandbox.config

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource
import com.sksamuel.hoplite.env.Environment

fun currentEnvironment(): Environment {
    val developmentFlag = System.getProperty("io.ktor.development")
    if (developmentFlag == "true") {
        return Environment.development
    }
    return Environment.fromEnvVar("KTOR_ENV", Environment.development)
}

inline fun <reified T : Any> loadApplicationProperties(): T {
    val environment = currentEnvironment()
    return loadApplicationProperties<T>(environment.name)
}

inline fun <reified T : Any> loadApplicationProperties(suffix: String): T {
    return loadProperties<T>("application", suffix)
}

inline fun <reified T : Any> loadProperties(prefix: String, suffix: String): T {
    return ConfigLoaderBuilder.default()
        .addResourceSource("/${prefix}.yaml")
        .addResourceSource("/${prefix}-${suffix}.yaml")
        .build()
        .loadConfigOrThrow<T>()
}
