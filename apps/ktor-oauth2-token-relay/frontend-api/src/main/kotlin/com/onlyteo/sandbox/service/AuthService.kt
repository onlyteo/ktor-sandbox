package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.config.buildLogger
import com.onlyteo.sandbox.model.OAuth2TokenResponse
import com.onlyteo.sandbox.properties.ApplicationProperties
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.basicAuth
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.http.isSuccess
import io.ktor.server.auth.OAuth2RequestParameters
import java.io.IOException

class AuthService(
    private val properties: ApplicationProperties,
    private val httpClient: HttpClient
) {
    private val logger = buildLogger

    suspend fun getOAuth2Token(refreshToken: String): OAuth2TokenResponse {
        logger.info("Fetching OAuth2 tokens")
        with(properties.security.oauth2.provider) {
            val response = httpClient.submitForm(tokenUrl) {
                basicAuth(clientId, clientSecret)
                formData {
                    append(OAuth2RequestParameters.GrantType, "refresh_token")
                    append(OAuth2RequestParameters.ClientId, clientId)
                    append(OAuth2RequestParameters.ClientSecret, clientSecret)
                    append("refresh_token", refreshToken)
                }
            }
            if (!response.status.isSuccess()) {
                throw IOException("Access token query failed with http status ${response.status}")
            }
            return response.body<OAuth2TokenResponse>()
        }
    }
}