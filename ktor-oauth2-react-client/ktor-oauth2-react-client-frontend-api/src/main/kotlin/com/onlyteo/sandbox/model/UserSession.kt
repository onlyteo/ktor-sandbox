package com.onlyteo.sandbox.model

import kotlinx.serialization.Serializable

@Serializable
data class UserSession(
    val state: String,
    val token: String
)