package com.onlyteo.sandbox.context

import com.onlyteo.sandbox.config.hikariDataSource
import com.onlyteo.sandbox.config.loadProperties
import com.onlyteo.sandbox.properties.ApplicationProperties
import com.onlyteo.sandbox.properties.ApplicationPropertiesHolder
import com.onlyteo.sandbox.repository.GreetingRepository
import com.onlyteo.sandbox.repository.PersonRepository
import com.onlyteo.sandbox.repository.PrefixRepository
import com.onlyteo.sandbox.service.GreetingService
import org.ktorm.database.Database
import javax.sql.DataSource

data class ApplicationContext(
    val properties: ApplicationProperties = loadProperties<ApplicationPropertiesHolder>().app,
    val dataSource: DataSource = hikariDataSource(properties),
    val database: Database = Database.connect(dataSource),
    val prefixRepository: PrefixRepository = PrefixRepository(properties.resources.prefixesFile),
    val personRepository: PersonRepository = PersonRepository(database),
    val greetingRepository: GreetingRepository = GreetingRepository(database),
    val greetingService: GreetingService = GreetingService(prefixRepository, personRepository, greetingRepository)
)
