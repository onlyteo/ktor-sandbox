package com.onlyteo.sandbox.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

val buildObjectMapper: ObjectMapper
    get() = jacksonObjectMapper {
        enable(KotlinFeature.NullToEmptyMap)
        enable(KotlinFeature.NullToEmptyCollection)
    }.apply {
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
        registerModules(JavaTimeModule())
    }
