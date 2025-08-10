package com.seros.domain.usecase

import com.seros.domain.model.RegisterData
import com.seros.domain.model.User
import com.seros.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(data: RegisterData): Result<User> {
        return repository.register(data)
    }
}
