package com.seros.domain.repository

import com.seros.domain.model.RegisterData
import com.seros.domain.model.User

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<User>
    suspend fun loginLocal(username: String, password: String): Result<User>
    suspend fun register(data: RegisterData): Result<User>
    suspend fun recoverPassword(email: String): Result<Unit>
    suspend fun getCurrentUserOffline(): User?
    suspend fun logout()
    suspend fun logoutLocal()
    suspend fun isUserLoggedIn(): Boolean
}