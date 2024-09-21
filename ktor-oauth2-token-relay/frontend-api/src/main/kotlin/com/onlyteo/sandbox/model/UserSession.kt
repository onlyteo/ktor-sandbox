package com.onlyteo.sandbox.model

import io.ktor.server.auth.Principal

data class UserSession(
    val token: String?
) : Principal