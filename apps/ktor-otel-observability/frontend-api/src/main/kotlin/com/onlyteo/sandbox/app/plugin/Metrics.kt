package com.onlyteo.sandbox.app.plugin

import com.onlyteo.sandbox.app.context.ApplicationContext
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.metrics.micrometer.MicrometerMetrics
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics
import io.micrometer.core.instrument.binder.system.ProcessorMetrics
import io.micrometer.core.instrument.binder.system.UptimeMetrics
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig
import java.time.Duration

fun Application.configureMetrics(applicationContext: ApplicationContext) {
    with(applicationContext) {
        install(MicrometerMetrics) {
            registry = prometheusMeterRegistry
            meterBinders = listOf(
                JvmGcMetrics(),
                JvmMemoryMetrics(),
                JvmThreadMetrics(),
                ProcessorMetrics(),
                UptimeMetrics()
            )
            distributionStatisticConfig = DistributionStatisticConfig.Builder()
                .percentilesHistogram(true)
                .minimumExpectedValue(Duration.ofMillis(20).toNanos().toDouble())
                .maximumExpectedValue(Duration.ofMillis(500).toNanos().toDouble())
                .serviceLevelObjectives(
                    Duration.ofMillis(100).toNanos().toDouble(),
                    Duration.ofMillis(500).toNanos().toDouble()
                )
                .build()
        }
    }
}