package com.seros.presentation.ui.auth.forgot_password

sealed class RPEvent {
    data class OnEmailChanged(val value: String) : RPEvent()
    data object OnEmailErrorCleared : RPEvent()
    data object OnRecoverClick : RPEvent()
    data object OnDialogDismiss : RPEvent()

}