package com.seros.presentation.ui.auth.register

data class RegisterUiState(
    val username: String = "",
    val name: String = "",
    val email: String = "",
    val dateOfBirth: String = "",
    val photoUrl: String = "",
    val phone: String = "",
    val password: String = "",
    val usernameError: String? = null,
    val nameError: String? = null,
    val emailError: String? = null,
    val dateOfBirthError: String? = null,
    val photoUrlError: String? = null,
    val phoneError: String? = null,
    val passwordError: String? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isRegistered: Boolean = false,
    val isPasswordVisible: Boolean = false,
)
