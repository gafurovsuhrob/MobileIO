package com.seros.domain.usecase

import com.seros.domain.model.User
import com.seros.domain.repository.AuthRepository

class GetOfflineUserUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): User? {
        return repository.getCurrentUserOffline()
    }
}
