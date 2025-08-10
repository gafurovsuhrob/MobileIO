package com.seros.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseDto(
    val name: String,
    val username: String,
    val password: String,
    val email: String,
    val phone: String,
    val dateOfBirth: String,
    val photoUrl: String
)