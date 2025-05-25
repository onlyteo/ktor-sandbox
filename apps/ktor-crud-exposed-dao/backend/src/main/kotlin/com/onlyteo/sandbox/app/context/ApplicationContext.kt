package com.onlyteo.sandbox.app.context

import com.onlyteo.sandbox.lib.database.factory.buildHikariDataSource
import com.onlyteo.sandbox.app.properties.ApplicationProperties
import com.onlyteo.sandbox.app.properties.ApplicationPropertiesHolder
import com.onlyteo.sandbox.properties.loadProperties
import com.onlyteo.sandbox.app.repository.GreetingRepository
import com.onlyteo.sandbox.app.repository.PersonRepository
import com.onlyteo.sandbox.app.repository.PrefixRepository
import com.onlyteo.sandbox.app.service.GreetingService
import javax.sql.DataSource

data class ApplicationContext(
    val properties: ApplicationProperties = loadProperties<ApplicationPropertiesHolder>().app,
    val hikariDataSource: DataSource = buildHikariDataSource(properties.dataSource),
    val prefixRepository: PrefixRepository = PrefixRepository(properties.resources.prefixesFile),
    val personRepository: PersonRepository = PersonRepository(),
    val greetingRepository: GreetingRepository = GreetingRepository(),
    val greetingService: GreetingService = GreetingService(prefixRepository, personRepository, greetingRepository)
)
