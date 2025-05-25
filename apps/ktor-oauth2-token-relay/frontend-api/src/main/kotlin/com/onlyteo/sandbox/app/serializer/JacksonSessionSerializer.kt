package com.onlyteo.sandbox.app.serializer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.onlyteo.sandbox.app.model.UserSession
import com.onlyteo.sandbox.lib.serialization.factory.buildObjectMapper
import io.ktor.server.sessions.SessionSerializer

class JacksonSessionSerializer(
    private val objectMapper: ObjectMapper = buildObjectMapper
) : SessionSerializer<UserSession> {
    override fun serialize(session: UserSession): String {
        return objectMapper.writeValueAsString(session)
    }

    override fun deserialize(text: String): UserSession {
        return objectMapper.readValue<UserSession>(text)
    }
}