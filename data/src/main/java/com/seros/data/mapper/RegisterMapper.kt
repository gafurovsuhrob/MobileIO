package com.seros.data.mapper

import com.seros.data.remote.dto.request.RegisterRequestDto
import com.seros.domain.model.RegisterData


fun RegisterData.toDto(): RegisterRequestDto {
    return RegisterRequestDto(
        name = name,
        username = username,
        password = password,
        email = email,
        phone = phone,
        dateOfBirth = dateOfBirth,
        photoUrl = photoUrl
    )
}
