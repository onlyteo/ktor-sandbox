package com.onlyteo.sandbox

import com.onlyteo.sandbox.config.configSupplier
import com.onlyteo.sandbox.plugin.configureRouting
import com.onlyteo.sandbox.plugin.configureSerialization
import com.onlyteo.sandbox.plugin.configureWebjars
import com.onlyteo.sandbox.plugin.httpClientSupplier
import com.onlyteo.sandbox.plugin.requestCacheSupplier
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    val config = configSupplier
    val httpClient = httpClientSupplier
    val requestCache = requestCacheSupplier
    configureSerialization()
    configureRouting(config, httpClient, requestCache)
    configureWebjars()
}