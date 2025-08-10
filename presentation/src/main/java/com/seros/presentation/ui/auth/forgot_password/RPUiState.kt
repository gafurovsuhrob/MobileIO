package com.seros.presentation.ui.auth.forgot_password

data class RPUiState(
    val email: String = "",
    val emailError: String? = null,
    val isLoading: Boolean = false,
    val isRecovered: Boolean = false,
)
