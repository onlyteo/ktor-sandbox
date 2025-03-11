package com.onlyteo.sandbox.model

import com.onlyteo.sandbox.model.tables.records.GreetingsRecord

data class Greeting(
    val message: String
)

fun GreetingsRecord.toGreeting(): Greeting =
    message?.let { Greeting(it) } ?: throw IllegalStateException("Field not defined")
