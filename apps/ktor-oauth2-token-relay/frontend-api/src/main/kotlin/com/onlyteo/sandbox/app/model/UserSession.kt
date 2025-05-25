package com.onlyteo.sandbox.app.model

import com.auth0.jwt.JWT
import io.ktor.server.auth.OAuthAccessTokenResponse
import java.time.Instant

data class UserSession(
    val accessToken: AccessToken,
    val refreshToken: String,
    val expiresAt: Long
)

fun OAuthAccessTokenResponse.OAuth2.asUserSession(): UserSession {
    val accessToken = JWT.decode(this.accessToken).asAccessToken()
    val refreshToken = this.refreshToken ?: throw IllegalStateException("Refresh token is not present")
    val expiresAt = Instant.now().plusSeconds(this.expiresIn)
    return UserSession(accessToken, refreshToken, expiresAt.toEpochMilli())
}