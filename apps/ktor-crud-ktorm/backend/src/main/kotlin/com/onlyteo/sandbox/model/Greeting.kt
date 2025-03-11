package com.onlyteo.sandbox.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

data class Greeting(
    val message: String
)

open class GreetingsTable(alias: String?) : Table<GreetingEntity>("GREETINGS", alias) {
    val id = int("ID").primaryKey().bindTo { it.id }
    val message = varchar("MESSAGE").bindTo { it.message }
    val personId = int("PERSON_ID").references(PersonsTable) { it.person }

    override fun aliased(alias: String) = GreetingsTable(alias)

    companion object : GreetingsTable(null)
}

interface GreetingEntity : Entity<GreetingEntity> {
    val id: Int
    var message: String
    var person: PersonEntity

    companion object : Entity.Factory<GreetingEntity>()
}

fun GreetingEntity.toGreeting() = Greeting(message)

val Database.greetings get() = this.sequenceOf(GreetingsTable)
