package com.onlyteo.sandbox.errors.model

import io.ktor.http.HttpStatusCode

/**
 * This is an error response class that implements the format of the Problem Details specification of RFC9457.
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc9457">IETF RFC9457</a>
 */
data class ProblemDetails(
    val type: String,
    val status: Int,
    val title: String,
    val detail: String?,
    val instance: String
) {
    constructor(httpStatusCode: HttpStatusCode, detail: String?, instance: String) : this(
        type = "about:blank",
        status = httpStatusCode.value,
        title = httpStatusCode.description,
        detail = detail,
        instance = instance
    )
}
