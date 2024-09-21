package com.onlyteo.sandbox.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline val <reified T> T.buildLogger: Logger get() = LoggerFactory.getLogger(T::class.java)

val applicationLogger: Logger get() = LoggerFactory.getLogger("com.onlyteo.logger.application")
val errorLogger: Logger get() = LoggerFactory.getLogger("com.onlyteo.logger.error")