package com.onlyteo.sandbox.app.properties

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
data class ApplicationProperties(
    @field:Valid @field:NotBlank val security: SecurityProperties
)

data class SecurityProperties(
    @field:Valid @field:NotBlank val whitelistedPaths: List<String>
)