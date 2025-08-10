package com.seros.data.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    val name: String,
    val username: String,
    val password: String,
    val email: String,
    val phone: String,
    val dateOfBirth: String,
    val photoUrl: String
)
