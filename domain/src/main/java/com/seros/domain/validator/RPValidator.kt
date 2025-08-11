package com.seros.domain.validator

object RPValidator {
    fun validateEmail(email: String): String? {
        if (email.isBlank()) return "Field is required"
        val emailRegex = """^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$""".toRegex()
        if (!emailRegex.matches(email)) return "Wrong email format"
        return null
    }
}