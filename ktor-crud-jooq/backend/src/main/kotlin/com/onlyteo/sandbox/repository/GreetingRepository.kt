package com.onlyteo.sandbox.repository

import com.onlyteo.sandbox.model.tables.records.GreetingsRecord
import com.onlyteo.sandbox.model.tables.references.GREETINGS
import com.onlyteo.sandbox.model.tables.references.PERSONS
import org.jooq.DSLContext
import java.util.concurrent.atomic.AtomicReference

class GreetingRepository(
    private val context: DSLContext
) {
    private val g = GREETINGS
    private val p = PERSONS

    fun findGreetings(name: String): List<GreetingsRecord> {
        with(context) {
            return select()
                .from(g).join(p)
                .on(p.ID.eq(g.PERSON_ID))
                .where(p.NAME.eq(name))
                .orderBy(p.ID.asc())
                .fetch()
                .into(GreetingsRecord::class.java)
        }
    }

    fun insertGreeting(message: String, personId: Int): GreetingsRecord {
        val atomicRecord = AtomicReference<GreetingsRecord>()
        context.transaction { outer ->
            val outerTx = outer.dsl()
            outerTx.insertInto(GREETINGS, GREETINGS.MESSAGE, GREETINGS.PERSON_ID)
                .values(message, personId)
                .execute().let { if (it == 0) throw IllegalStateException("Could not insert greeting") }
            val id = outerTx.lastID().toInt()

            outerTx.transaction { inner ->
                val innerTx = inner.dsl()
                val record = innerTx.selectFrom(g)
                    .where(g.ID.eq(id))
                    .fetchOne() ?: throw IllegalStateException("Could not find greeting")
                atomicRecord.set(record)
            }
        }
        return atomicRecord.get()
    }
}