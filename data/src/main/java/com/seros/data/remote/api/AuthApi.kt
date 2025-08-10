package com.seros.data.remote.api

import android.util.Log
import com.seros.data.local.manager.TokenManager
import com.seros.data.remote.client.NetworkClient
import com.seros.data.remote.client.NetworkResult
import com.seros.data.remote.dto.request.LoginDto
import com.seros.data.remote.dto.request.RegisterRequestDto
import com.seros.data.remote.dto.response.AuthResponseDto
import com.seros.data.util.LOGIN
import com.seros.data.util.SIGN_UP

class AuthApi(
    private val client: NetworkClient,
    private val tokenManager: TokenManager
) {

    suspend fun login(username: String, password: String): AuthResponseDto {
        return when (
            val result = client.post<AuthResponseDto, LoginDto>(
                endpoint = LOGIN,
                body = LoginDto(
                    username = username,
                    password = password
                ),
                authorized = false
            )
        ) {
            is NetworkResult.Success -> {
                result.data
            }
            is NetworkResult.HttpError -> throw Exception("HTTP ${result.code} ${result.message}")
            is NetworkResult.NetworkError -> throw result.exception
            NetworkResult.Unauthorized -> throw Exception("401 Unauthorized")
        }
    }

    suspend fun register(data: RegisterRequestDto): AuthResponseDto {
        return when (
            val result = client.post<AuthResponseDto, RegisterRequestDto>(
                endpoint = SIGN_UP,
                body = data,
                authorized = false
            )
        ) {
            is NetworkResult.Success -> {
                result.data
            }
            is NetworkResult.HttpError -> throw Exception("HTTP ${result.code} ${result.message}")
            is NetworkResult.NetworkError -> throw result.exception
            NetworkResult.Unauthorized -> throw Exception("401 Unauthorized")
        }
    }

    suspend fun recoverPassword(email: String) {
        client.post<Unit, Map<String, String>>(
            endpoint = "/auth/recover",
            body = mapOf("email" to email),
            authorized = false
        )
    }
}
