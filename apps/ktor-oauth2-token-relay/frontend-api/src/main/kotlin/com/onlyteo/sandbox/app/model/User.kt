package com.onlyteo.sandbox.app.model

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.SerialName

data class User(@field:JsonProperty("sub") val subject: String)

data class UserInfo(
    val id: String,
    val name: String,
    @SerialName("given_name")
    val givenName: String,
    @SerialName("family_name")
    val familyName: String,
    val picture: String,
    val locale: String
)