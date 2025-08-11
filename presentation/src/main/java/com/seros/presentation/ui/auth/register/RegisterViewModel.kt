package com.seros.presentation.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seros.domain.model.RegisterData
import com.seros.domain.usecase.RegisterUseCase
import com.seros.domain.validator.RegisterValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val validator: RegisterValidator
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnUsernameChanged -> _uiState.update { it.copy(username = event.value) }
            is RegisterEvent.OnPasswordChanged -> _uiState.update { it.copy(password = event.value) }
            is RegisterEvent.OnNameChanged -> _uiState.update { it.copy(name = event.value) }
            is RegisterEvent.OnEmailChanged -> _uiState.update { it.copy(email = event.value) }
            is RegisterEvent.OnPhoneChanged -> _uiState.update { it.copy(phone = event.value) }
            is RegisterEvent.OnDateOfBirthChanged -> _uiState.update { it.copy(dateOfBirth = event.value) }
            is RegisterEvent.OnPhotoUrlChanged -> _uiState.update { it.copy(photoUrl = event.value) }
            is RegisterEvent.OnUsernameErrorCleared -> _uiState.update { it.copy(usernameError = null) }
            is RegisterEvent.OnNameErrorCleared -> _uiState.update { it.copy(nameError = null) }
            is RegisterEvent.OnEmailErrorCleared -> _uiState.update { it.copy(emailError = null) }
            is RegisterEvent.OnDateOfBirthErrorCleared -> _uiState.update { it.copy(dateOfBirthError = null) }
            is RegisterEvent.OnPhotoUrlErrorCleared -> _uiState.update { it.copy(photoUrlError = null) }
            is RegisterEvent.OnPhoneErrorCleared -> _uiState.update { it.copy(phoneError = null) }
            is RegisterEvent.OnPasswordErrorCleared -> _uiState.update { it.copy(passwordError = null) }
            is RegisterEvent.OnPasswordVisibilityToggle -> {
                _uiState.value = _uiState.value.copy(
                    isPasswordVisible = !_uiState.value.isPasswordVisible
                )
            }
            is RegisterEvent.OnRegisterClick -> register()
        }
    }

    private fun register() {
        if (!validate()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val data = RegisterData(
                username = _uiState.value.username,
                password = _uiState.value.password,
                name = _uiState.value.name,
                email = _uiState.value.email,
                phone = _uiState.value.phone,
                dateOfBirth = _uiState.value.dateOfBirth,
                photoUrl = "https://avatars.githubusercontent.com/u/98651376?s=400&u=0157d9a99b792c07bbc8f802a0aaf049cbb15548&v=4"
            )

            val result = registerUseCase(data)

            result.onSuccess {
                _uiState.update { it.copy(isLoading = false, isRegistered = true) }
            }.onFailure {
                _uiState.update { it.copy(isLoading = false, errorMessage = it.errorMessage) }
            }
        }
    }

    private fun validate(): Boolean {
        val usernameError = validator.validateUsername(_uiState.value.username)
        val nameError = validator.validateName(_uiState.value.name)
        val emailError = validator.validateEmail(_uiState.value.email)
        val phoneError = validator.validatePhone(_uiState.value.phone)
        val passwordError = validator.validatePassword(_uiState.value.password)
        val dateOfBirthError = validator.validateDateOfBirth(_uiState.value.dateOfBirth)

        _uiState.update { it.copy(
            usernameError = usernameError,
            nameError = nameError,
            emailError = emailError,
            phoneError = phoneError,
            passwordError = passwordError,
            dateOfBirthError = dateOfBirthError
        ) }

        return usernameError == null &&
                nameError == null &&
                emailError == null &&
                phoneError == null &&
                passwordError == null &&
                dateOfBirthError == null
    }
}
