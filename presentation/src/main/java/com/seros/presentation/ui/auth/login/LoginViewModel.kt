package com.seros.presentation.ui.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seros.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnUsernameChanged -> {
                _uiState.value = _uiState.value.copy(username = event.value)
            }
            is LoginEvent.OnPasswordChanged -> {
                _uiState.value = _uiState.value.copy(password = event.value)
            }
            is LoginEvent.OnUsernameErrorCleared -> {
                _uiState.value = _uiState.value.copy(usernameError = null)
            }
            is LoginEvent.OnPasswordErrorCleared -> {
                _uiState.value = _uiState.value.copy(passwordError = null)
            }
            is LoginEvent.OnPasswordVisibilityToggle -> {
                _uiState.value = _uiState.value.copy(
                    isPasswordVisible = !_uiState.value.isPasswordVisible
                )
            }

            is LoginEvent.OnRememberMeToggle -> {
                _uiState.value = _uiState.value.copy(
                    isRememberMeChecked = !_uiState.value.isRememberMeChecked
                )
            }

            LoginEvent.OnLoginClick -> {
                login()
            }
        }
    }

    private fun login() {
        val usernameError = LoginValidator.validateUsername(_uiState.value.username.trim())
        val passwordError = LoginValidator.validatePassword(_uiState.value.password)

        if (usernameError != null || passwordError != null) {
            _uiState.value = _uiState.value.copy(
                usernameError = usernameError,
                passwordError = passwordError
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = loginUseCase(
                username = _uiState.value.username.trim(),
                password = _uiState.value.password
            )

            result.onSuccess {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoggedIn = true
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    passwordError = it.message
                )
            }
        }
    }
}
