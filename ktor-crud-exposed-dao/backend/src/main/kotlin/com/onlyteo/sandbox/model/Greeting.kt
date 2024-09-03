package com.onlyteo.sandbox.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

data class Greeting(
    val message: String
)

object GreetingsTable : IntIdTable("GREETINGS") {
    val message = varchar("MESSAGE", 500)
    val personId = reference("PERSON_ID", PersonsTable)
}

class GreetingEntity(id: EntityID<Int>) : IntEntity(id) {
    var message by GreetingsTable.message
    var person by PersonEntity referencedOn GreetingsTable.personId

    companion object : IntEntityClass<GreetingEntity>(GreetingsTable)
}

fun GreetingEntity.toGreeting() = Greeting(message)
