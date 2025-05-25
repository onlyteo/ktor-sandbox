package com.onlyteo.sandbox.app.model

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement

data class Greeting(
    val message: String
)

object GreetingsTable : IntIdTable("GREETINGS") {
    val message = varchar("MESSAGE", 500)
    val personId = reference("PERSON_ID", PersonsTable)
}

fun ResultRow.toGreeting() = Greeting(get(GreetingsTable.message))

fun InsertStatement<Number>.toGreeting() = Greeting(get(GreetingsTable.message))
