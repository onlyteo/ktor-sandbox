package com.onlyteo.sandbox.errors.model

import io.ktor.http.HttpStatusCode
import java.net.URI

val DEFAULT_TYPE: URI = URI.create("urn:unknown:error")

/**
 * This is an error response class that implements the format of the Problem Details specification of RFC9457.
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc9457">IETF RFC9457</a>
 */
data class ProblemDetails(
    val type: URI = DEFAULT_TYPE,
    val status: Int,
    val title: String,
    val detail: String?,
    val instance: String
) {
    constructor(type: URI = DEFAULT_TYPE, httpStatusCode: HttpStatusCode, detail: String?, instance: String) : this(
        type = type,
        status = httpStatusCode.value,
        title = httpStatusCode.description,
        detail = detail,
        instance = instance
    )
}
