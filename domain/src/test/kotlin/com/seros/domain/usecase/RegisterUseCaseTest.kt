package com.seros.domain.usecase

import com.seros.domain.model.RegisterData
import com.seros.domain.model.User
import com.seros.domain.repository.AuthRepository
import com.seros.domain.usecase.RegisterUseCase
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class RegisterUseCaseTest {

    private val repository: AuthRepository = mockk()
    private val useCase = RegisterUseCase(repository)

    @AfterEach
    fun tearDown() {
        clearMocks(repository)
    }

    @Test
    fun `register should return success when repository returns success`() = runBlocking {
        val registerData = RegisterData(
            username = "testUser",
            password = "pass123",
            name = "Test Name",
            email = "test@mail.com",
            phone = "+992123456789",
            dateOfBirth = "01/01/2000",
            photoUrl = "https://example.com/avatar.jpg"
        )

        val expectedUser = User(
            id = 1,
            username = "testUser",
            email = "test@mail.com",
            name = "Test Name",
            phone = "+992123456789",
            dateOfBirth = "01/01/2000",
            photoUrl = "https://example.com/avatar.jpg",
            token = "token_abc"
        )

        val expectedResult = Result.success(expectedUser)

        coEvery { repository.register(registerData) } returns expectedResult

        val result = useCase(registerData)

        assertTrue(result.isSuccess)
        assertEquals(expectedUser, result.getOrNull())
        coVerify { repository.register(registerData) }
    }

    @Test
    fun `register should return failure when repository throws exception`() = runBlocking {
        val registerData = RegisterData(
            "testUser",
            "pass123",
            "name",
            "test@mail.com",
            "+992123456789",
            "01/01/2000",
            "https://example.com/avatar.jpg"
        )
        val expectedError = Exception("User already exists")

        coEvery { repository.register(registerData) } returns Result.failure(expectedError)

        val result = useCase(registerData)

        assertTrue(result.isFailure)
        assertEquals(expectedError, result.getOrNull())
        coVerify { repository.register(registerData) }
    }
}
