package com.onlyteo.sandbox.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher

@Configuration(proxyBeanMethods = false)
class AuthorizationServerConfig {

    /**
     * The [SecurityFilterChain] defines how the application should be protected using Spring Security.
     * By using the DSL of the [HttpSecurity] configuration builder it is possible to customize the security
     * setup.
     * Here the application is configured as an OAuth2 Authorization Server extended with Open ID Connect capabilities.
     * This customizes the basic OAuth2 Resource Server configuration with Authorization Server capabilities.
     * See the JavaDoc of the [OAuth2AuthorizationServerConfigurer] class for more details.
     *
     * @param http - HTTP security configuration builder.
     * @return The [SecurityFilterChain] bean.
     * @throws Exception -
     */
    @Bean
    @Order(1)
    @Throws(Exception::class)
    fun authorizationServerSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer()
        return http
            .securityMatcher(authorizationServerConfigurer.endpointsMatcher)
            .with(authorizationServerConfigurer) { config: OAuth2AuthorizationServerConfigurer ->
                config
                    .oidc(Customizer.withDefaults())    // Enable OpenID Connect 1.0
            }
            .authorizeHttpRequests { config ->
                config
                    .anyRequest().authenticated()
            }
            .exceptionHandling { config ->
                config
                    .defaultAuthenticationEntryPointFor(
                        LoginUrlAuthenticationEntryPoint("/login"),
                        MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                    )
            }
            .build()
    }
}