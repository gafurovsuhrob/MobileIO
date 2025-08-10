package com.seros.presentation.components

data class TextFieldState(
    val text: String = "",
    val error: String? = null
) {
    val isError get() = !error.isNullOrEmpty()
}