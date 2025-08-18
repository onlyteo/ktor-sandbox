package com.onlyteo.sandbox.app.model

import com.fasterxml.jackson.annotation.JsonProperty

data class OAuth2TokenResponse(
    @field:JsonProperty("access_token") val accessToken: String,
    @field:JsonProperty("refresh_token") val refreshToken: String,
    @field:JsonProperty("token_type") val tokenType: String,
    @field:JsonProperty("expires_in") val expiresIn: Long
)