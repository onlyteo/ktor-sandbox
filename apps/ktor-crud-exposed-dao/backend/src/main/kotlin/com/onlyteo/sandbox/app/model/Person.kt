package com.onlyteo.sandbox.app.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

data class Person(val name: String)

object PersonsTable : IntIdTable("PERSONS") {
    val name = varchar("NAME", 50).uniqueIndex()
}

class PersonEntity(id: EntityID<Int>) : IntEntity(id) {
    var name by PersonsTable.name
    val greetings by GreetingEntity referrersOn GreetingsTable.personId

    companion object : IntEntityClass<PersonEntity>(PersonsTable)
}
