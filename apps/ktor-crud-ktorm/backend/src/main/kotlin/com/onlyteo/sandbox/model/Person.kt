package com.onlyteo.sandbox.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

data class Person(val name: String)

open class PersonsTable(alias: String?) : Table<PersonEntity>("PERSONS", alias) {
    val id = int("ID").primaryKey().bindTo { it.id }
    val name = varchar("NAME").bindTo { it.name }

    override fun aliased(alias: String) = PersonsTable(alias)

    companion object : PersonsTable(null)
}

interface PersonEntity : Entity<PersonEntity> {
    val id: Int
    var name: String

    companion object : Entity.Factory<PersonEntity>()
}

val Database.persons get() = this.sequenceOf(PersonsTable)
