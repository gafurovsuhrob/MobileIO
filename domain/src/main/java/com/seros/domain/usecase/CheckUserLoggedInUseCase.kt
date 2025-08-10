package com.seros.domain.usecase

import com.seros.domain.repository.AuthRepository


class CheckUserLoggedInUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        return authRepository.isUserLoggedIn()
    }
}
