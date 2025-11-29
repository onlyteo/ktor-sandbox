package com.onlyteo.sandbox.app.model

import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.core.statements.InsertStatement

data class Greeting(
    val message: String
)

object GreetingsTable : IntIdTable("GREETINGS") {
    val message = varchar("MESSAGE", 500)
    val personId = reference("PERSON_ID", PersonsTable)
}

fun ResultRow.toGreeting() = Greeting(get(GreetingsTable.message))

fun InsertStatement<Number>.toGreeting() = Greeting(get(GreetingsTable.message))
