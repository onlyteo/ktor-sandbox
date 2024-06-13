package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.model.toGreeting

class GreetingService() {

    suspend fun getGreeting(person: Person): Greeting {
        return person.toGreeting()
    }
}