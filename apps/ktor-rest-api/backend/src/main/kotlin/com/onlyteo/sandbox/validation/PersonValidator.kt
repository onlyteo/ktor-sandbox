package com.onlyteo.sandbox.validation

import com.onlyteo.sandbox.model.Person
import io.ktor.server.plugins.requestvalidation.ValidationResult

object PersonValidator : Validator<Person> {
    override suspend fun validate(value: Person): ValidationResult = with(value) {
        if (name.isBlank()) {
            ValidationResult.Invalid("Name is blank")
        } else {
            ValidationResult.Valid
        }
    }
}