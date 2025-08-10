package com.seros.presentation.ui.auth.login

sealed class LoginEvent {
    data class OnUsernameChanged(val value: String) : LoginEvent()
    data class OnPasswordChanged(val value: String) : LoginEvent()
    data object OnUsernameErrorCleared : LoginEvent()
    data object OnPasswordErrorCleared : LoginEvent()
    data object OnPasswordVisibilityToggle : LoginEvent()
    data object OnLoginClick : LoginEvent()
    data object OnRememberMeToggle : LoginEvent()
}
