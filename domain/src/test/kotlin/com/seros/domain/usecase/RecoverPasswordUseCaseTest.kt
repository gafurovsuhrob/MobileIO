package com.seros.domain.usecase

import com.seros.domain.repository.AuthRepository
import com.seros.domain.usecase.RecoverPasswordUseCase
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class RecoverPasswordUseCaseTest {

    private val repository: AuthRepository = mockk()
    private val useCase = RecoverPasswordUseCase(repository)

    @AfterEach
    fun tearDown() {
        clearMocks(repository)
    }

    @Test
    fun `recoverPassword should call repository`() = runBlocking {
        val email = "test@mail.com"
        coEvery { repository.recoverPassword(email) } returns Result.success(Unit)

        useCase(email)

        coVerify(exactly = 1) { repository.recoverPassword(email) }
    }
}
