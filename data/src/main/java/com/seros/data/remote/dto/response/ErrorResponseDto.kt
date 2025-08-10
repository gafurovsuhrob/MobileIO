package com.seros.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseDto(
    val message: String? = null,
    val details: String? = null
)
