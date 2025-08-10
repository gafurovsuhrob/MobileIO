package com.seros.presentation.ui.splash


sealed class SplashUiState {
    data object Loading : SplashUiState()
    data class Success(val isLoggedIn: Boolean) : SplashUiState()
}