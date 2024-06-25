package com.onlyteo.sandbox.config

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource

inline fun <reified T : Any> loadProperties(file: String): T {
    val path = "/" + file.removePrefix("/")
    return ConfigLoaderBuilder.default()
        .addResourceSource(path)
        .build()
        .loadConfigOrThrow<T>()
}