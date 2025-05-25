package com.onlyteo.sandbox.app.plugin

import com.onlyteo.sandbox.app.model.Person
import com.onlyteo.sandbox.app.validation.PersonValidator
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<Person>(PersonValidator::validate)
    }
}