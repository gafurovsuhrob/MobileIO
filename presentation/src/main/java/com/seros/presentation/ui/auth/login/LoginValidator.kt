package com.seros.presentation.ui.auth.login

object LoginValidator {
    fun validateUsername(username: String): String? {
        if (username.isBlank()) return "Field is required"
        if (username.length < 3) return "Minimum 3 characters"
        return null
    }

    fun validatePassword(password: String): String? {
        if (password.isBlank()) return "Password is required"
        return null
    }
}
