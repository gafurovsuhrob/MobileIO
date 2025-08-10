package com.seros.data.remote.client

import android.util.Log
import com.seros.data.local.manager.TokenManager
import com.seros.data.util.BASE_URL
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.TimeoutCancellationException

class NetworkClient(
    val httpClient: HttpClient,
    val tokenManager: TokenManager
) {

    suspend inline fun <reified T> get(
        endpoint: String,
        headers: Map<String, String> = emptyMap(),
        queryParams: Map<String, String> = emptyMap(),
        authorized: Boolean = true
    ): NetworkResult<T> = safeRequest("GET", endpoint, authorized = authorized) {
        httpClient.get {
            url {
                takeFrom(BASE_URL + endpoint)
                queryParams.forEach { (k, v) -> parameters.append(k, v) }
            }
            setupHeaders(headers, authorized)
        }.body()
    }

    suspend inline fun <reified T, reified Body> post(
        endpoint: String,
        body: Body? = null,
        headers: Map<String, String> = emptyMap(),
        queryParams: Map<String, String> = emptyMap(),
        authorized: Boolean = true
    ): NetworkResult<T> = safeRequest("POST", endpoint, logBody = body, authorized = authorized) {
        httpClient.post {
            url {
                takeFrom(BASE_URL + endpoint)
                queryParams.forEach { (k, v) -> parameters.append(k, v) }
            }
            setupHeaders(headers, authorized)
            contentType(ContentType.Application.Json)
            body?.let { setBody(it) }
        }.body()
    }

    suspend inline fun <reified T, reified Body> put(
        endpoint: String,
        body: Body? = null,
        headers: Map<String, String> = emptyMap(),
        queryParams: Map<String, String> = emptyMap(),
        authorized: Boolean = true
    ): NetworkResult<T> = safeRequest("PUT", endpoint, logBody = body, authorized = authorized) {
        httpClient.put {
            url {
                takeFrom(BASE_URL + endpoint)
                queryParams.forEach { (k, v) -> parameters.append(k, v) }
            }
            setupHeaders(headers, authorized)
            contentType(ContentType.Application.Json)
            body?.let { setBody(it) }
        }.body()
    }

    suspend inline fun <reified T> patch(
        endpoint: String,
        headers: Map<String, String> = emptyMap(),
        queryParams: Map<String, String> = emptyMap(),
        authorized: Boolean = true
    ): NetworkResult<T> = safeRequest("PATCH", endpoint, authorized = authorized) {
        httpClient.patch {
            url {
                takeFrom(BASE_URL + endpoint)
                queryParams.forEach { (k, v) -> parameters.append(k, v) }
            }
            setupHeaders(headers, authorized)
        }.body()
    }

    suspend inline fun <reified T> delete(
        endpoint: String,
        headers: Map<String, String> = emptyMap(),
        queryParams: Map<String, String> = emptyMap(),
        authorized: Boolean = true
    ): NetworkResult<T> = safeRequest("DELETE", endpoint, authorized = authorized) {
        httpClient.delete {
            url {
                takeFrom(BASE_URL + endpoint)
                queryParams.forEach { (k, v) -> parameters.append(k, v) }
            }
            setupHeaders(headers, authorized)
        }.body()
    }

    suspend fun HttpRequestBuilder.setupHeaders(
        customHeaders: Map<String, String>,
        authorized: Boolean
    ) {
        customHeaders.forEach { (key, value) -> header(key, value) }
        if (authorized) {
            tokenManager.getAccessToken()?.let { token ->
                header(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }

    suspend fun <T> safeRequest(
        method: String,
        endpoint: String,
        logBody: Any? = null,
        authorized: Boolean = true,
        block: suspend () -> T
    ): NetworkResult<T> {
        val fullUrl = BASE_URL + endpoint
        val tag = "NetworkClient"

        try {
            Log.d(tag, "→ [$method] $fullUrl")
            logBody?.let { Log.d(tag, "Request Body: $it") }

            val response = block()

            Log.d(tag, "← [$method] $fullUrl: SUCCESS")
            Log.d(tag, "Response: $response")

            return NetworkResult.Success(response)

        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.Unauthorized && authorized) {
                Log.e(tag, "401 Unauthorized. Попытка обновить токен…")
                val retryResult = tryRefreshTokenAndRetry(block)
                if (retryResult != null) return retryResult
            }
            Log.e(tag, "← [$method] $fullUrl: HTTP ERROR - ${e.response.status.value} ${e.message}")
            Log.e(tag, "Пароль или логин неверны. Проверьте введенные данные")
            Log.e(tag, "Время жизни токена истекло. Перезагрузите приложение")
            return NetworkResult.HttpError(e.response.status.value, e.message)
        } catch (e: ResponseException) {
            Log.e(tag, "← [$method] $fullUrl: HTTP ERROR - ${e.response.status.value} ${e.message}")
            return NetworkResult.HttpError(e.response.status.value, e.message)
        } catch (e: IOException) {
            Log.e(tag, "← [$method] $fullUrl: NETWORK ERROR - ${e.message}")
            return NetworkResult.NetworkError(e)
        } catch (e: TimeoutCancellationException) {
            Log.e(tag, "← [$method] $fullUrl: TIMEOUT ERROR - ${e.message}")
            return NetworkResult.NetworkError(e)
        } catch (e: Exception) {
            Log.e(tag, "${e.message}")
            Log.e(tag, "← [$method] $fullUrl: UNKNOWN ERROR - ${e.message ?: e::class.simpleName}")
            return NetworkResult.NetworkError(e)
        }
    }

    private suspend fun <T> tryRefreshTokenAndRetry(
        block: suspend () -> T
    ): NetworkResult<T>? {
        val refreshToken = tokenManager.getRefreshToken() ?: return null

        return try {

            Log.d("TokenManager", "Обновляем токен через refresh_token...")
            tokenManager.deleteAccessToken()
            null
        } catch (e: Exception) {
            Log.e("TokenManager", "Ошибка при обновлении токена: ${e.message}")
            null
        }
    }
}