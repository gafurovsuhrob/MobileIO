package com.seros.data.mapper

import com.seros.data.local.entity.UserEntity
import com.seros.data.remote.dto.response.UserDto
import com.seros.domain.model.User


fun User.toEntity(passwordHash: String? = null): UserEntity{
    return UserEntity(
        id = id,
        username = username,
        name = name,
        email = email,
        phone = phone,
        dateOfBirth = dateOfBirth,
        photoUrl = photoUrl,
        passwordHash = passwordHash,
        token = token)
}

fun UserDto.toEntity(token: String, passwordHash: String? = null): UserEntity {
    return UserEntity(
        id = id,
        username = username,
        name = name,
        email = email,
        phone = phone,
        dateOfBirth = dateOfBirth,
        photoUrl = photoUrl,
        token = token,
        passwordHash = passwordHash
    )
}

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        username = username,
        name = name,
        email = email,
        phone = phone,
        dateOfBirth = dateOfBirth,
        photoUrl = photoUrl,
        token = token
    )
}

fun UserEntity.toDto(): UserDto {
    return UserDto(
        id = id,
        username = username,
        name = name,
        email = email,
        phone = phone,
        dateOfBirth = dateOfBirth,
        photoUrl = photoUrl
    )
}
