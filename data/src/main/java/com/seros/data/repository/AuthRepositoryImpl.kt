package com.seros.data.repository

import android.util.Log
import at.favre.lib.crypto.bcrypt.BCrypt
import com.seros.data.local.dao.UserDao
import com.seros.data.local.manager.TokenManager
import com.seros.data.mapper.toDomain
import com.seros.data.mapper.toDto
import com.seros.data.mapper.toEntity
import com.seros.data.remote.api.AuthApi
import com.seros.data.system.NetworkChecker
import com.seros.domain.model.RegisterData
import com.seros.domain.model.User
import com.seros.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val userDao: UserDao,
    private val tokenManager: TokenManager,
    private val networkChecker: NetworkChecker
) : AuthRepository {

    override suspend fun login(username: String, password: String): Result<User> {
        return withContext(Dispatchers.IO) {
            val hasInternet = networkChecker.isNetworkAvailable()

            if (hasInternet) {
                try {
                    val response = api.login(username, password)

                    val hash = BCrypt.withDefaults().hashToString(10, password.toCharArray())
                    val userEntity = response.user.toEntity(
                        token = response.token,
                        passwordHash = hash
                    )
                    userDao.insert(userEntity)

                    tokenManager.saveAccessToken(response.token)
                    return@withContext Result.success(userEntity.toDomain())
                } catch (e: Exception) {
                    return@withContext loginLocal(username, password)
                }
            }else{
                return@withContext loginLocal(username, password)
            }
        }
    }

    override suspend fun loginLocal(username: String, password: String): Result<User> {
        return withContext(Dispatchers.IO) {
            try {
                val userX = userDao.getUser()?.toDomain()
                Log.d("LoginLocalUseCase", "userX: $userX")

                val user = userDao.getUserByUsername(username)
                Log.d("LoginLocalUseCase", "user: $user")

                if (user != null &&
                    BCrypt.verifyer().verify(password.toCharArray(), user.passwordHash).verified
                ) {
                    val hash = BCrypt.withDefaults().hashToString(10, password.toCharArray())
                    Log.d("LoginLocalUseCase", "password hash: $hash")

                    Log.d("LoginLocalUseCase", "password: ${user.passwordHash}")
                    tokenManager.saveAccessToken(user.passwordHash.toString())
                    Result.success(user.toDomain())
                } else {
                    Result.failure(Exception("User not found or password incorrect"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }


    override suspend fun register(data: RegisterData): Result<User> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.register(data.toDto())
                val userEntity = response.user.toEntity(
                    token = response.token,
                    passwordHash = data.password
                )
                userDao.insert(userEntity)
                Result.success(userEntity.toDomain())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }


    override suspend fun recoverPassword(email: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                api.recoverPassword(email)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getCurrentUserOffline(): User? {
        return withContext(Dispatchers.IO) {
            userDao.getUser()?.toDomain()
        }
    }

    override suspend fun logout() {
        userDao.clear()
        tokenManager.deleteAccessToken()
    }

    override suspend fun logoutLocal() {
        tokenManager.deleteAccessToken()
    }

    override suspend fun isUserLoggedIn(): Boolean {
        val token = tokenManager.getAccessToken()
        return !token.isNullOrBlank()
    }
}