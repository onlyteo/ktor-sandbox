package com.onlyteo.sandbox.model

data class Person(val name: String)

fun Person.toGreeting() = Greeting("Hello $name!")
