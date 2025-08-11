package com.seros.domain.usecase

import com.seros.domain.model.User
import com.seros.domain.repository.AuthRepository
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class LoginUseCaseTest {

    private val repository: AuthRepository = mockk()
    private val useCase = LoginUseCase(repository)

    @AfterEach
    fun tearDown() {
        clearMocks(repository)
    }

    @Test
    fun `login should return success when repository returns success`() = runBlocking {
        val username = "testUser"
        val password = "testPass"
        val user = mockk<User>(relaxed = true)
        val expectedResult = Result.success(user)

        coEvery { repository.login(username, password) } returns expectedResult

        val result = useCase(username, password)

        assertTrue(result.isSuccess)
        assertEquals(expectedResult, result.getOrNull())
        coVerify { repository.login(username, password) }
    }

    @Test
    fun `login should return failure when repository throws exception`() = runBlocking {
        val username = "testUser"
        val password = "wrongPass"
        val expectedError = Exception("Invalid credentials")

        coEvery { repository.login(username, password) } returns Result.failure(expectedError)

        val result = useCase(username, password)

        assertTrue(result.isFailure)
        assertEquals(expectedError, result.getOrNull())
        coVerify { repository.login(username, password) }
    }
}

