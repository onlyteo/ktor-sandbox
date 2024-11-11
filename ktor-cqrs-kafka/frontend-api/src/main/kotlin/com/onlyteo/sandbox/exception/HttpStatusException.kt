package com.onlyteo.sandbox.exception

import io.ktor.http.HttpStatusCode

class HttpStatusException(
    val status: HttpStatusCode,
    override val message: String? = null,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)