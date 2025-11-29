package com.onlyteo.sandbox.app.model

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

data class Person(val name: String)

object PersonsTable : IntIdTable("PERSONS") {
    val name = varchar("NAME", 50).uniqueIndex()
}

class PersonEntity(id: EntityID<Int>) : IntEntity(id) {
    var name by PersonsTable.name
    val greetings by GreetingEntity referrersOn GreetingsTable.personId

    companion object : IntEntityClass<PersonEntity>(PersonsTable)
}
