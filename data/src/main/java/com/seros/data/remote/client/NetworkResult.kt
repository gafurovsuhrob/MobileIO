package com.seros.data.remote.client

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T): NetworkResult<T>()
    data class HttpError(val code: Int, val message: String?): NetworkResult<Nothing>()
    data class NetworkError(val exception: Throwable): NetworkResult<Nothing>()
    object Unauthorized : NetworkResult<Nothing>()
}