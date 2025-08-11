package com.seros.presentation.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.seros.domain.model.User
import com.seros.domain.usecase.CheckUserLoggedInUseCase
import com.seros.domain.usecase.GetOfflineUserUseCase
import com.seros.domain.usecase.LogoutLocalUseCase
import com.seros.domain.usecase.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel (
    private val getOfflineUserUseCase: GetOfflineUserUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val logoutLocalUseCase: LogoutLocalUseCase
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    init {
        viewModelScope.launch {
            _currentUser.value = getOfflineUserUseCase.invoke()
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.invoke()
            _currentUser.value = null
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            _currentUser.value = getOfflineUserUseCase.invoke()

            Log.d("MainViewModel", "currentUser: ${_currentUser.value}")
        }
    }

    fun logoutLocal() {
        viewModelScope.launch {
            logoutLocalUseCase.invoke()
            _currentUser.value = null
        }
    }
}