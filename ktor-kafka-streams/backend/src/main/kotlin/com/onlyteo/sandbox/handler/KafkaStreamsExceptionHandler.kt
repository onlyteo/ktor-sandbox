package com.onlyteo.sandbox.handler

import com.onlyteo.sandbox.config.buildLogger
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler

class KafkaStreamsExceptionHandler : StreamsUncaughtExceptionHandler {

    private val logger = buildLogger

    override fun handle(throwable: Throwable): StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse {
        logger.error("Uncaught Kafka Streams exception", throwable)
        return StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse.SHUTDOWN_APPLICATION
    }
}

fun KafkaStreams.withUncaughtExceptionHandler(exceptionHandler: StreamsUncaughtExceptionHandler): KafkaStreams {
    setUncaughtExceptionHandler(exceptionHandler)
    return this
}