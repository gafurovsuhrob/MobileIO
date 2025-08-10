package com.seros.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(
    val password: String,
    val username: String
)