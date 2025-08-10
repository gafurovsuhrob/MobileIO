package com.seros.presentation.ui.auth.register

import com.seros.presentation.ui.auth.login.LoginEvent

sealed class RegisterEvent {
    data class OnUsernameChanged(val value: String) : RegisterEvent()
    data class OnNameChanged(val value: String) : RegisterEvent()
    data class OnEmailChanged(val value: String) : RegisterEvent()
    data class OnDateOfBirthChanged(val value: String) : RegisterEvent()
    data class OnPhotoUrlChanged(val value: String) : RegisterEvent()
    data class OnPhoneChanged(val value: String) : RegisterEvent()
    data class OnPasswordChanged(val value: String) : RegisterEvent()
    data object OnUsernameErrorCleared : RegisterEvent()
    data object OnNameErrorCleared : RegisterEvent()
    data object OnEmailErrorCleared : RegisterEvent()
    data object OnDateOfBirthErrorCleared : RegisterEvent()
    data object OnPhotoUrlErrorCleared : RegisterEvent()
    data object OnPhoneErrorCleared : RegisterEvent()
    data object OnPasswordErrorCleared : RegisterEvent()
    data object OnPasswordVisibilityToggle : RegisterEvent()
    data object OnRegisterClick : RegisterEvent()
}
