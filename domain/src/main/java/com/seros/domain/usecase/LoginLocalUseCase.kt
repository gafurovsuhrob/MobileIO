package com.seros.domain.usecase

import com.seros.domain.model.User
import com.seros.domain.repository.AuthRepository

class LoginLocalUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<User> {
        return repository.loginLocal(username, password)
    }
}
