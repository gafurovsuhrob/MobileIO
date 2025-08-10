package com.seros.domain.model


data class RegisterData(
    val username: String,
    val password: String,
    val name: String,
    val email: String,
    val phone: String,
    val dateOfBirth: String,
    val photoUrl: String
)
