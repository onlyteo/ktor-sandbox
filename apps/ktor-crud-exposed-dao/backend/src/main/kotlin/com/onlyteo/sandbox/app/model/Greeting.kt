package com.onlyteo.sandbox.app.model

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

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
