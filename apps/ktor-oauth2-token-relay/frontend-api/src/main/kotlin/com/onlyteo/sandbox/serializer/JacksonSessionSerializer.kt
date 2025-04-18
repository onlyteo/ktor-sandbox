package com.onlyteo.sandbox.serializer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.onlyteo.sandbox.model.UserSession
import com.onlyteo.sandbox.serialization.factory.buildObjectMapper
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