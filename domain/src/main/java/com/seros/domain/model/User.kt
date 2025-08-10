package com.seros.domain.model

data class User(
    val id: Long,
    val username: String,
    val name: String,
    val email: String,
    val phone: String,
    val dateOfBirth: String,
    val photoUrl: String,
    val token: String
)
