package com.onlyteo.sandbox.context

import com.onlyteo.sandbox.database.factory.buildHikariDataSource
import com.onlyteo.sandbox.properties.ApplicationProperties
import com.onlyteo.sandbox.properties.ApplicationPropertiesHolder
import com.onlyteo.sandbox.properties.loadProperties
import com.onlyteo.sandbox.repository.GreetingRepository
import com.onlyteo.sandbox.repository.PersonRepository
import com.onlyteo.sandbox.repository.PrefixRepository
import com.onlyteo.sandbox.service.GreetingService
import javax.sql.DataSource

data class ApplicationContext(
    val properties: ApplicationProperties = loadProperties<ApplicationPropertiesHolder>().app,
    val hikariDataSource: DataSource = buildHikariDataSource(properties.dataSource),
    val prefixRepository: PrefixRepository = PrefixRepository(properties.resources.prefixesFile),
    val personRepository: PersonRepository = PersonRepository(),
    val greetingRepository: GreetingRepository = GreetingRepository(),
    val greetingService: GreetingService = GreetingService(prefixRepository, personRepository, greetingRepository)
)
