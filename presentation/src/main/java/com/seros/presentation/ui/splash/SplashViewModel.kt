package com.seros.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seros.domain.usecase.CheckUserLoggedInUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel (
    private val checkUserLoggedInUseCase: CheckUserLoggedInUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val uiState: StateFlow<SplashUiState> = _uiState

    private val scope = CoroutineScope(Dispatchers.Default)

    init {
        load()
    }

    private fun load() {
        scope.launch {
            val isLoggedIn = checkUserLoggedInUseCase.invoke()
            _uiState.value = SplashUiState.Success(isLoggedIn)
        }
    }
}
