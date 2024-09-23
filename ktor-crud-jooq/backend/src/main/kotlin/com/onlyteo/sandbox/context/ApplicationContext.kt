package com.onlyteo.sandbox.context

import com.onlyteo.sandbox.config.hikariDataSource
import com.onlyteo.sandbox.config.loadProperties
import com.onlyteo.sandbox.properties.ApplicationProperties
import com.onlyteo.sandbox.properties.ApplicationPropertiesHolder
import com.onlyteo.sandbox.repository.GreetingRepository
import com.onlyteo.sandbox.repository.PersonRepository
import com.onlyteo.sandbox.repository.PrefixRepository
import com.onlyteo.sandbox.service.GreetingService
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import javax.sql.DataSource

data class ApplicationContext(
    val properties: ApplicationProperties = loadProperties<ApplicationPropertiesHolder>().app,
    val dataSource: DataSource = hikariDataSource(properties),
    val dslContext: DSLContext = DSL.using(dataSource, SQLDialect.POSTGRES),
    val prefixRepository: PrefixRepository = PrefixRepository(properties.resources.prefixesFile),
    val personRepository: PersonRepository = PersonRepository(dslContext),
    val greetingRepository: GreetingRepository = GreetingRepository(dslContext),
    val greetingService: GreetingService = GreetingService(prefixRepository, personRepository, greetingRepository)
)
