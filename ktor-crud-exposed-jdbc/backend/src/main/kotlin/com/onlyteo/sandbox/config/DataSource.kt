package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.properties.DataSourceProperties
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

fun DataSourceProperties.buildHikariDataSource(): DataSource {
    val config = HikariConfig()
    config.driverClassName = driverClassName
    config.jdbcUrl = url
    config.username = username
    config.password = password
    config.maximumPoolSize = maximumPoolSize
    config.isAutoCommit = autoCommit
    config.transactionIsolation = transactionIsolation
    config.validate()
    return HikariDataSource(config)
}