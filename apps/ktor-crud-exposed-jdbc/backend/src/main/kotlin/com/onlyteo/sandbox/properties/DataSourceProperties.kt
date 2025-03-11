package com.onlyteo.sandbox.properties

data class DataSourceProperties(
    val driverClassName: String,
    val url: String,
    val username: String,
    val password: String,
    val maximumPoolSize: Int = 3,
    val autoCommit: Boolean = false,
    val transactionIsolation: String = "TRANSACTION_REPEATABLE_READ"
)
