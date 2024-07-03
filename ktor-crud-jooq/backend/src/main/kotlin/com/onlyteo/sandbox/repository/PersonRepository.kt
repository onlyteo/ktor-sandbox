package com.onlyteo.sandbox.repository

import com.onlyteo.sandbox.model.tables.records.PersonsRecord
import com.onlyteo.sandbox.model.tables.references.PERSONS
import org.jooq.DSLContext
import java.util.concurrent.atomic.AtomicReference

class PersonRepository(
    private val context: DSLContext
) {
    private val p = PERSONS

    fun findPerson(name: String): PersonsRecord? {
        with(context) {
            return selectFrom(p)
                .where(p.NAME.eq(name))
                .fetchOne()
        }
    }

    fun insertPerson(name: String): PersonsRecord {
        val atomicRecord = AtomicReference<PersonsRecord>()
        context.transaction { outer ->
            val outerTx = outer.dsl()
            outerTx.insertInto(p, p.NAME)
                .values(name)
                .execute().let { if (it == 0) throw IllegalStateException("Could not insert person") }
            val id = outerTx.lastID().toInt()

            outerTx.transaction { inner ->
                val innerTx = inner.dsl()
                val record = innerTx.selectFrom(p)
                    .where(p.ID.eq(id))
                    .fetchOne() ?: throw IllegalStateException("Could not find person")
                atomicRecord.set(record)
            }
        }
        return atomicRecord.get()
    }
}