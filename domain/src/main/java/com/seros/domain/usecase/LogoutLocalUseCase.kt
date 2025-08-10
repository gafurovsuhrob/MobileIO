package com.seros.domain.usecase

import com.seros.domain.repository.AuthRepository

class LogoutLocalUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke() {
        repository.logoutLocal()
    }
}