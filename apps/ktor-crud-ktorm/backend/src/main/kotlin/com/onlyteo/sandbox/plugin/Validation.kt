package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.validation.PersonValidator
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<Person>(PersonValidator::validate)
    }
}