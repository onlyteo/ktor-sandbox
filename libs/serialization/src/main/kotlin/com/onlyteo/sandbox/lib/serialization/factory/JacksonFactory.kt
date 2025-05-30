package com.onlyteo.sandbox.lib.serialization.factory

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

val buildObjectMapper: ObjectMapper
    get() = jacksonObjectMapper().apply {
        configureJackson()
    }

fun ObjectMapper.configureJackson() {
    setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
    disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)
    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
    registerModule(JavaTimeModule())
    registerKotlinModule {
        withReflectionCacheSize(512)
        disable(KotlinFeature.NullIsSameAsDefault)
        disable(KotlinFeature.SingletonSupport)
        disable(KotlinFeature.StrictNullChecks)
        enable(KotlinFeature.NullToEmptyCollection)
        enable(KotlinFeature.NullToEmptyMap)
    }
}