package com.onlyteo.sandbox.app.repository

import com.onlyteo.sandbox.model.tables.records.PersonsRecord
import com.onlyteo.sandbox.model.tables.references.PERSONS
import org.jooq.DSLContext
import java.util.concurrent.atomic.AtomicReference

class PersonRepository(
    private val dslContext: DSLContext
) {

    fun findPerson(name: String): PersonsRecord? {
        with(dslContext) {
            return selectFrom(PERSONS)
                .where(PERSONS.NAME.eq(name))
                .fetchOne()
        }
    }

    fun insertPerson(name: String): PersonsRecord {
        val atomicRecord = AtomicReference<PersonsRecord>()
        dslContext.transaction { outer ->
            val outerTx = outer.dsl()
            outerTx.insertInto(PERSONS, PERSONS.NAME)
                .values(name)
                .execute().let { if (it == 0) throw IllegalStateException("Could not insert person") }
            val id = outerTx.lastID().toInt()

            outerTx.transaction { inner ->
                val innerTx = inner.dsl()
                val record = innerTx.selectFrom(PERSONS)
                    .where(PERSONS.ID.eq(id))
                    .fetchOne() ?: throw IllegalStateException("Could not find person")
                atomicRecord.set(record)
            }
        }
        return atomicRecord.get()
    }
}