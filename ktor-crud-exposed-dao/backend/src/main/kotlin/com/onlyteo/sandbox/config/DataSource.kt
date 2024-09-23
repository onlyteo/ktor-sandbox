package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.context.ApplicationContext
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

fun hikariDataSource(context: ApplicationContext): DataSource {
    with(context.properties.dataSource) {
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
}