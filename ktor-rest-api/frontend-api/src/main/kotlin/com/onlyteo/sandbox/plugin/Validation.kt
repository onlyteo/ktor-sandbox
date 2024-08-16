package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.model.Person
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<Person> { person ->
            with(person) {
                if (name.isBlank()) {
                    ValidationResult.Invalid("Name is blank")
                } else {
                    ValidationResult.Valid
                }
            }
        }
    }
}