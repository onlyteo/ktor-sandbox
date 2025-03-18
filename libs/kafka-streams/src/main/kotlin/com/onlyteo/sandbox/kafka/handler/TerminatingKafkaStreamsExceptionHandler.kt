package com.onlyteo.sandbox.kafka.handler

import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler
import org.slf4j.LoggerFactory

class TerminatingKafkaStreamsExceptionHandler : StreamsUncaughtExceptionHandler {
    private val logger = LoggerFactory.getLogger("com.onlyteo.logger.error")

    override fun handle(throwable: Throwable): StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse {
        logger.error("Uncaught Kafka Streams exception", throwable)
        return StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse.SHUTDOWN_APPLICATION
    }
}