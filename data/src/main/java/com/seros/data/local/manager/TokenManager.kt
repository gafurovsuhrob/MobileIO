package com.seros.data.local.manager

interface TokenManager {
    suspend fun saveAccessToken(token: String)
    suspend fun getAccessToken(): String?
    suspend fun deleteAccessToken()

    suspend fun saveRefreshToken(token: String)
    suspend fun getRefreshToken(): String?
    suspend fun deleteRefreshToken()
}

