package com.seros.data.repository

import at.favre.lib.crypto.bcrypt.BCrypt
import com.seros.data.local.dao.UserDao
import com.seros.data.local.manager.TokenManager
import com.seros.data.mapper.toDto
import com.seros.data.mapper.toEntity
import com.seros.data.remote.api.AuthApi
import com.seros.data.remote.dto.response.AuthResponseDto
import com.seros.data.system.NetworkChecker
import com.seros.domain.model.User
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryImplTest {

    private val api: AuthApi = mockk()
    private val userDao: UserDao = mockk()
    private val tokenManager: TokenManager = mockk()
    private val networkChecker: NetworkChecker = mockk()

    private lateinit var repository: AuthRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = AuthRepositoryImpl(api, userDao, tokenManager, networkChecker)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `login success with internet`() = runTest {
        val username = "user1"
        val password = "pass123"
        val token = "token_abc"

        val domainUser = User(
            id = 1,
            username = "testUser",
            email = "test@mail.com",
            name = "Test Name",
            phone = "+992123456789",
            dateOfBirth = "01/01/2000",
            photoUrl = "https://example.com/avatar.jpg",
            token = "token_abc"
        )
        val userEntity = domainUser.toEntity(
            passwordHash = BCrypt.withDefaults().hashToString(10, password.toCharArray())
        )

        val apiResponse = AuthResponseDto(
            userEntity.toDto(),
            token
        )

        coEvery { networkChecker.isNetworkAvailable() } returns true
        coEvery { api.login(username, password) } returns apiResponse
        coEvery { userDao.insert(any()) } just Runs
        coEvery { tokenManager.saveAccessToken(token) } just Runs

        val result = repository.login(username, password)

        Assertions.assertTrue(result.isSuccess)
        Assertions.assertEquals(domainUser.username, result.getOrNull()?.username)

        coVerifySequence {
            networkChecker.isNetworkAvailable()
            api.login(username, password)
            userDao.insert(any())
            tokenManager.saveAccessToken(token)
        }
    }

    @Test
    fun `login fallback to local when no internet`() = runTest {
        val username = "user1"
        val password = "pass123"
        val passwordHash = BCrypt.withDefaults().hashToString(10, password.toCharArray())

        val domainUser = User(
            id = 1,
            username = "user1",
            email = "test@mail.com",
            name = "Test Name",
            phone = "+992123456789",
            dateOfBirth = "01/01/2000",
            photoUrl = "https://example.com/avatar.jpg",
            token = "token_abc",
        )

        val userEntity = domainUser.toEntity(passwordHash = passwordHash)

        coEvery { networkChecker.isNetworkAvailable() } returns false
        coEvery { userDao.getUserByUsername(username) } returns userEntity
        coEvery { tokenManager.saveAccessToken(any()) } just Runs

        val result = repository.login(username, password)

        Assertions.assertTrue(result.isSuccess)
        Assertions.assertEquals(username, result.getOrNull()?.username)
    }


}
