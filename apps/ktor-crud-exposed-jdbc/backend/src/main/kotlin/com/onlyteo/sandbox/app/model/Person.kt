package com.onlyteo.sandbox.app.model

import org.jetbrains.exposed.dao.id.IntIdTable

data class Person(val name: String)

object PersonsTable : IntIdTable("PERSONS") {
    val name = varchar("NAME", 50).uniqueIndex()
}

