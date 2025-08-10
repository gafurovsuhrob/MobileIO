package com.seros.presentation.ui.auth.login

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val usernameError: String? = null,
    val passwordError: String? = null,
    val isPasswordVisible: Boolean = false,
    val isRememberMeChecked: Boolean = false,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false
)
