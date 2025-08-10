package com.seros.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Long,
    val username: String,
    val name: String,
    val email: String,
    val phone: String,
    val dateOfBirth: String,
    val photoUrl: String,
    val token: String,
    val passwordHash: String? = null
)