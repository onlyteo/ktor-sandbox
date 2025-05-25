package com.onlyteo.sandbox.app.validation

import io.ktor.server.plugins.requestvalidation.ValidationResult

interface Validator<T> {
    suspend fun validate(value: T): ValidationResult
}