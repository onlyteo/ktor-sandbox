package com.onlyteo.sandbox.app.model

import com.auth0.jwt.interfaces.DecodedJWT
import kotlinx.serialization.Serializable

@Serializable
data class AccessToken(
    val id: String,
    val issuer: String,
    val subject: String,
    val audience: List<String>,
    val issuedAt: Long,
    val expiresAt: Long,
    val token: String,
)

fun DecodedJWT.asAccessToken(): AccessToken = AccessToken(
    id = id,
    issuer = issuer,
    subject = subject,
    audience = audience,
    issuedAt = issuedAtAsInstant.toEpochMilli(),
    expiresAt = expiresAtAsInstant.toEpochMilli(),
    token = token
)