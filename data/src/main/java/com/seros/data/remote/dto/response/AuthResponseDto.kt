package com.seros.data.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDto(
    @SerialName("user") val user: UserDto,
    @SerialName("token") val token: String
)