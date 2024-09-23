package com.onlyteo.sandbox.model

import com.auth0.jwt.JWT
import io.ktor.server.auth.OAuthAccessTokenResponse
import io.ktor.server.auth.Principal
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class UserSession(
    val accessToken: AccessToken,
    val refreshToken: String,
    val expiresAt: Long
) : Principal

fun OAuthAccessTokenResponse.OAuth2.asUserSession(): UserSession {
    val accessToken = JWT.decode(this.accessToken).asAccessToken()
    val refreshToken = this.refreshToken ?: throw IllegalStateException("Refresh token is not present")
    val expiresAt = Instant.now().plusSeconds(this.expiresIn)
    return UserSession(accessToken, refreshToken, expiresAt.toEpochMilli())
}