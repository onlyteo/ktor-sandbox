package com.onlyteo.sandbox.context

import org.slf4j.Logger
import org.slf4j.LoggerFactory

const val APPLICATION_LOGGER_NAME = "com.onlyteo.sandbox.application"

data class LoggingContext(
    val logger: Logger = LoggerFactory.getLogger(APPLICATION_LOGGER_NAME),
)
