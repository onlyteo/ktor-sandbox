package no.onlyteo.sandbox.logging.factory

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline val <reified T> T.buildLogger: Logger get() = LoggerFactory.getLogger(T::class.java)

val buildApplicationLogger: Logger get() = LoggerFactory.getLogger("com.onlyteo.logger.application")
val buildSecurityLogger: Logger get() = LoggerFactory.getLogger("com.onlyteo.logger.security")
val buildErrorLogger: Logger get() = LoggerFactory.getLogger("com.onlyteo.logger.error")