package com.onlyteo.sandbox.config

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource

inline fun <reified T : Any> loadProperties(file: String): T {
    return ConfigLoaderBuilder.default()
        .addResourceSource("/${file.removePrefix("/")}")
        .build()
        .loadConfigOrThrow<T>()
}