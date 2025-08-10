package com.seros.presentation.ui.auth.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seros.domain.usecase.RecoverPasswordUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RPViewModel(
    private val recoverPasswordUseCase: RecoverPasswordUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(RPUiState())
    val uiState: StateFlow<RPUiState> = _uiState

    fun onEvent(event: RPEvent) {
        when (event) {
            is RPEvent.OnEmailChanged -> {
                _uiState.value = _uiState.value.copy(email = event.value)
            }
            is RPEvent.OnEmailErrorCleared -> {
                _uiState.value = _uiState.value.copy(emailError = null)
            }
            is RPEvent.OnDialogDismiss -> {
                _uiState.value = _uiState.value.copy(isRecovered = false)
            }
            RPEvent.OnRecoverClick -> {
                recoverPassword()
            }
        }
    }

    private fun validate(): Boolean {
        val emailError = RPValidator.validateEmail(_uiState.value.email)
        _uiState.value = _uiState.value.copy(emailError = emailError)
        return emailError == null
    }

    private fun recoverPassword() {
        if (!validate()) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            delay(1000)
            _uiState.value = _uiState.value.copy(isLoading = false, isRecovered = true)
        }
    }

}