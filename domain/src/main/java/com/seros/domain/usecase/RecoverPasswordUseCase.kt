package com.seros.domain.usecase

import com.seros.domain.repository.AuthRepository

class RecoverPasswordUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String): Result<Unit> {
        return repository.recoverPassword(email)
    }
}
