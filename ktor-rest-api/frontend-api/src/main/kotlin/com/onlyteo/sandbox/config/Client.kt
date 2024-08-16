package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.exception.ProblemDetailException
import com.onlyteo.sandbox.model.ProblemDetails
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.jackson.jackson

fun buildRestClient() = HttpClient(CIO) {
    install(ContentNegotiation) {
        jackson {
            configureJackson()
        }
        HttpResponseValidator {
            validateResponse { response ->
                if (response.status.value >= 400) {
                    val problemDetails: ProblemDetails = response.body()
                    throw ProblemDetailException(problemDetails)
                }
            }
        }
    }
}